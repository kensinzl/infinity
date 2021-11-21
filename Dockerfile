FROM postgres:latest as db
ENV POSTGRES_USER=kensin  
ENV POSTGRES_PASSWORD=123  
# database name
ENV POSTGRES_DB=DEV_DB


FROM openjdk:8-jdk-alpine as web_api

# Downloading and installing Maven
# 1- Define a constant with the version of maven you want to install
ARG MAVEN_VERSION=3.6.3         

RUN apk add --no-cache curl tar bash procps

# 2- Define a constant with the working directory
ARG USER_HOME_DIR="/root"

# 3- Define the SHA key to validate the maven download
ARG SHA=b4880fb7a3d81edd190a029440cdf17f308621af68475a4fe976296e71ff4a4b546dd6d8a58aaafba334d309cc11e638c52808a4b0e818fc0fd544226d952544

# 4- Define the URL where maven can be downloaded from
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

# 5- Create the directories, download maven, validate the download, install it, remove downloaded file and set links
RUN mkdir -p /usr/share/maven /usr/share/maven/ref
RUN echo "Downlaoding maven"
RUN curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz

#RUN echo "Checking download hash"
#RUN echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c -

RUN echo "Unziping maven"
RUN tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1

RUN echo "Cleaning and setting links"
RUN rm -f /tmp/apache-maven.tar.gz
RUN ln -s /usr/share/maven/bin/mvn /usr/bin/mvn


# 6- Define environmental variables required by Maven, like Maven_Home directory and where the maven repo is located
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

WORKDIR /build/
# 使用 WORKDIR 指令可以来指定工作目录（或者称为当前目录），以后各层的当前目录就被改为指定的目录，如该目录不存在，WORKDIR 会帮你建立目录。
# In this instance, you need to know what is the difference between the following
# WORKDIR /build/  -> when you enter the container, the first folder you enter is the build, like cd /build
# without WORKDIR  -> when you enter the container, the first folder you enter is the root, like cd /

# because using workdir /build/, the current path is /build/, so ./ that means current path(/build/)
COPY pom.xml ./
# need to know, this one just copy and do not enter the /build/ folder
COPY src ./src/

# Note: you need to know what is the difference between the following
# COPY src ./src/  -> copy the whole src folder among /build/
# that means, cd /build, you can see the whole src folder

# COPY src /build/      -> copy the sub_main and sub_test folder among /build/
# that means, cd /build, you can see the whole main and test folder

COPY entrypoint.sh ./

RUN chmod 777 ./entrypoint.sh

# ENTRYPOINT ["./entrypoint.sh"]
# docker-compose entrypoint can overwrite the entrypoint of dockerfile.
# here, entrypoint say hey where is our entrypoint.sh location
# so, remove the entrypoint tag from dockerfile and let it is defined from docker-compose