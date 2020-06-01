package nz.co.indepth.infinity.entity;

import javax.persistence.*;

/**
 * Owning Table(Store the foreign keys), Child Table
 */
@Entity
@Table(name = "EMAIL")
public class Email {

    @Id
    @Column(name = "email_id")
    @GeneratedValue
    private Long id;

    @Column(name = "email_address")
    private String emailAddress;

    /**
     * If do not introduce fieldName employeeId here, I have to use email.getEmployee().getId() to get employee id.
     * It would drag down the performance because have to load the employee instance.
     */
    @Column(name = "employee_id")
    private Long employeeId;

    /**
     * Note: @ManyToOne can not be existed lonely.
     *
     * @ManyToOne's
     *  1. fetch mode default value is eager, because just one record row
     *  2. optional default is true, true that means works like left join, false works like inner join.
     *
     * @JoinColumn
     *  1. name                 -> Owing table employee_id column
     *  2. referenceColumnName  -> Inverse table employee_id column
     *
     * Test case:
     *  1. Fetch Email should always gets Employee.
     *      -> Yes, fetch email will also fetch employee. Using findEmailBy.. or JPQL
     *  2. try delete email, to see employee.
     *      -> Delete email will also delete employee because of if add cascade all via using deleteEmailBy..
     *      -> Delete email will not delete employee if using JPQL "delete from email"
     *
     *  Summary for insertable:
     *      1. fieldName employeeId is set value, employee.id also set value, cascade all and make insertable true
     *         This situation will complain the repeated exception
     *
     *      2. fieldName employeeId is 9L, employee.id is 10L.
     *         employee is 10L record would be changed if employee changed the original value.
     *
     */

    /*
        // @Column(name = "LOCATION_ID") 

        // private Long locationId; 

         

        // 1. Sequence, insert location first => insert product_hierarchy second 

        // I assume insert location is fine, but when insert product_hierarchy, the JPA is confusing for multiple location_id. 

        // 2. the @JoinColumn(insertable = true) and location_id: break the rules of LOCATION_ID (should be mapped with insert="false" update="false") 

        // It make sense, JPA should do some check before inserting the DB 

        // 3. also, JPA would check whether the Cascade target entity location is transient or not before manipulating database 

        // eg: In the product_hierarchy, the product_hierarchy is kid but location is parent 

        // class product_hierarchy { 

        //    @MantToOne(cascade = CasacdeType.ALL) 

        //    Location location 

        // } 

        // 4. bear in mind, put cascade.all in the Kid entity is a bad practice, here is just test example. because when you delete one kid will cascade into parent, so how about other kids which become orphan. 

         

        // 1. (complain repeated column) 

        // 1) location_id is one id and location with null different location_id 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL) 

        // @JoinColumn(name = "LOCATION_ID", nullable = false, insertable = true, updatable = false) 

        // private Location location;      

        // Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: nz.co.niwa.mintaka.entity.product.ProductHierarchyItem column:  

        // LOCATION_ID (should be mapped with insert="false" update="false") 

        //ph.setLocationId(MintakaLocationInMemoryData.AREA_LOCATION_ONE.getLocationId()); 

        //ph.setLocation(location); 

         

        // 2) location_id is one id and location with null location_id 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false) 

        // @JoinColumn(name = "LOCATION_ID", nullable = false, insertable = true, updatable = false) 

        // private Location location;      

        // Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: nz.co.niwa.mintaka.entity.product.ProductHierarchyItem column:  

        // LOCATION_ID (should be mapped with insert="false" update="false") 

        //ph.setLocationId(MintakaLocationInMemoryData.AREA_LOCATION_ONE.getLocationId()); 

        //ph.setLocation(location); 

         

        // 3) location_id is one id and location with valid existed location_id 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL) 

        // @JoinColumn(name = "LOCATION_ID", nullable = false, insertable = true, updatable = false) 

        // private Location location;    

        // Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: nz.co.niwa.mintaka.entity.product.ProductHierarchyItem column:  

        // LOCATION_ID (should be mapped with insert="false" update="false") 

        //ph.setLocationId(MintakaLocationInMemoryData.AREA_LOCATION_ONE.getLocationId()); 

        //ph.setLocation(location); 

         

        // 4) location_id is one id and location with valid existed location_id 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false) 

        // @JoinColumn(name = "LOCATION_ID", nullable = false, insertable = true, updatable = false) 

        // private Location location;    

        // Caused by: org.hibernate.MappingException: Repeated column in mapping for entity: nz.co.niwa.mintaka.entity.product.ProductHierarchyItem column:  

        // LOCATION_ID (should be mapped with insert="false" update="false") 

        //ph.setLocationId(MintakaLocationInMemoryData.AREA_LOCATION_ONE.getLocationId()); 

        //ph.setLocation(location); 

         

         

        // @Column(name = "LOCATION_ID") 

        // private Long locationId; 

        // 2. 

        // 1) location_id is one id and location with null location_id => success, location is inserted but new product_hierarchy using ph.setLocationId because location insertable = false 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL) 

        // @JoinColumn(name = "LOCATION_ID", nullable = false, insertable = false, updatable = false) 

        // private Location location; 

        // ph.setLocationId(MintakaLocationInMemoryData.AREA_LOCATION_ONE.getLocationId()); 

        // ph.setLocation(location); 

         

        // 2) location_id is one id and location with null location_id => if no PK of Location plus do not cascade, this entity will be considered as transient 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false) 

        // @JoinColumn(name = "LOCATION_ID", nullable = false, insertable = false, updatable = false) 

        // private Location location; 

        // java.lang.IllegalStateException: org.hibernate.TransientPropertyValueException: Not-null property references a transient value - transient instance must be saved before current operation :  

        // nz.co.niwa.mintaka.entity.product.ProductHierarchyItem.location -> nz.co.niwa.mintaka.entity.location.Location 

        // ph.setLocationId(MintakaLocationInMemoryData.AREA_LOCATION_ONE.getLocationId()); 

        // ph.setLocation(location); 

         

        // 3) location_id is one id and location with valid location_id => success, location already existed so stay it and new product_hierarchy using ph.setLocationId because location insertable = false 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL) 

        // @JoinColumn(name = "LOCATION_ID", nullable = false, insertable = false, updatable = false) 

        // private Location location; 

        // ph.setLocationId(MintakaLocationInMemoryData.AREA_LOCATION_ONE.getLocationId()); 

        // ph.setLocation(location); 

         

        // 4) location_id is one id and location with valid location_id => success, location already existed so stay it and new product_hierarchy using ph.setLocationId because location insertable = false 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false) 

        // @JoinColumn(name = "LOCATION_ID", nullable = false, insertable = false, updatable = false) 

        // private Location location; 

        // ph.setLocationId(MintakaLocationInMemoryData.AREA_LOCATION_ONE.getLocationId()); 

        // ph.setLocation(location); 

         

         

         

         

        // 3.  

        // 1) location_id remove and location without id => product_hierarchy:location_id = inserted location:location_id 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL) 

        // @JoinColumn(name = "LOCATION_ID", nullable = true, insertable = true, updatable = false) 

        // private Location location; 

        // ph.setLocation(location); 

         

        // 2) location_id remove and location without id => when inserting product_hierarchy, complain location_id is null  

        // @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL) 

        // @JoinColumn(name = "LOCATION_ID", nullable = true, insertable = false, updatable = false) 

        // private Location location; 

        // org.h2.jdbc.JdbcSQLException: NULL not allowed for column "LOCATION_ID"; SQL statement: 

        // insert into PRODUCT_HIERARCHY (ALT_LABEL, CURRENT_FORECAST_START_TIME, DATA_END_TIME, DATA_START_TIME, DEFAULT_VISUALISATION_ID, DESCRIPTION, DEVICE_INFORMATION, DOMAIN_ID, EXTERNAL_PRODUCT_ID, FORECAST_MODEL_ID, INGESTION_FREQUENCY, INITIAL_EXTENT, LAST_INGESTION_TIME, LATEST_ANALYSIS_TIME, MAP_LLX, MAP_LLY, MAP_TYPE, MAP_URX, MAP_URY, NAME, OVERDUE_REFR_RATE, IS_PRIVATE, CLASS, PRODUCT_SUBCLASS_ID, PRODUCT_TYPE, PROJECTION_DESCRIPTION, PROJECTION_NAME, RANGE_ID, SCROLL_INCREMENT, TUPLE_SOURCE_ID, TUPLE_SOURCE_TYPE, VISIBILITY_ID, PRODUCT_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 

        //ph.setLocation(location); 

         

        // 3) location_id remove and location with valid id => success, new product_hierarchy:location_id = existed location:location_id 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL) 

        // @JoinColumn(name = "LOCATION_ID", nullable = true, insertable = true, updatable = false) 

        // private Location location; 

        //ph.setLocation(location); 

         

        // 4) location_id remove and location with valid id => when inserting product_hierarchy, complain location_id is null 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL) 

        // @JoinColumn(name = "LOCATION_ID", nullable = true, insertable = false, updatable = false) 

        // private Location location; 

        // Caused by: org.h2.jdbc.JdbcSQLException: NULL not allowed for column "LOCATION_ID"; SQL statement: 

        // insert into PRODUCT_HIERARCHY (ALT_LABEL, CURRENT_FORECAST_START_TIME, DATA_END_TIME, DATA_START_TIME, DEFAULT_VISUALISATION_ID, DESCRIPTION, DEVICE_INFORMATION, DOMAIN_ID, EXTERNAL_PRODUCT_ID, FORECAST_MODEL_ID, INGESTION_FREQUENCY, INITIAL_EXTENT, LAST_INGESTION_TIME, LATEST_ANALYSIS_TIME, MAP_LLX, MAP_LLY, MAP_TYPE, MAP_URX, MAP_URY, NAME, OVERDUE_REFR_RATE, IS_PRIVATE, CLASS, PRODUCT_SUBCLASS_ID, PRODUCT_TYPE, PROJECTION_DESCRIPTION, PROJECTION_NAME, RANGE_ID, SCROLL_INCREMENT, TUPLE_SOURCE_ID, TUPLE_SOURCE_TYPE, VISIBILITY_ID, PRODUCT_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  

        // ph.setLocation(location); 

         

        // 5) location_id remove and location with valid id 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false) => success, new product_hierarchy:location_id = existed location:location_id 

        // @JoinColumn(name = "LOCATION_ID", nullable = true, insertable = true, updatable = false) 

        // private Location location; 

        // ph.setLocation(location); 

         

        // 6) location_id remove and location with valid id  

        // @ManyToOne(fetch = FetchType.EAGER, optional = false) 

        // @JoinColumn(name = "LOCATION_ID", nullable = true, insertable = false, updatable = false) 

        // private Location location; 

        // Caused by: org.h2.jdbc.JdbcSQLException: NULL not allowed for column "LOCATION_ID"; SQL statement: 

        // insert into PRODUCT_HIERARCHY (ALT_LABEL, CURRENT_FORECAST_START_TIME, DATA_END_TIME, DATA_START_TIME, DEFAULT_VISUALISATION_ID, DESCRIPTION, DEVICE_INFORMATION, DOMAIN_ID, EXTERNAL_PRODUCT_ID, FORECAST_MODEL_ID, INGESTION_FREQUENCY, INITIAL_EXTENT, LAST_INGESTION_TIME, LATEST_ANALYSIS_TIME, MAP_LLX, MAP_LLY, MAP_TYPE, MAP_URX, MAP_URY, NAME, OVERDUE_REFR_RATE, IS_PRIVATE, CLASS, PRODUCT_SUBCLASS_ID, PRODUCT_TYPE, PROJECTION_DESCRIPTION, PROJECTION_NAME, RANGE_ID, SCROLL_INCREMENT, TUPLE_SOURCE_ID, TUPLE_SOURCE_TYPE, VISIBILITY_ID, PRODUCT_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  

        // ph.setLocation(location); 

         

        // 7) location_id remove and location without id => 1. insert location first then insert product_hierarchy because need location_id from locatio first 2. but actually insert ph then cascade inserting location instanly, but no cascade then compalin 

        // @ManyToOne(fetch = FetchType.EAGER, optional = false) 

        // @JoinColumn(name = "LOCATION_ID", nullable = true, insertable = false, updatable = false) 

        // private Location location; 

        // Caused by: org.hibernate.TransientPropertyValueException: Not-null property references a transient value - transient instance must be saved before current operation :  

        // nz.co.niwa.mintaka.entity.product.ProductHierarchyItem.location -> nz.co.niwa.mintaka.entity.location.Location 

        // ph.setLocation(location); 

         

        // 8) location_id remove and location without id =>  

        // @ManyToOne(fetch = FetchType.EAGER, optional = false) 

        // @JoinColumn(name = "LOCATION_ID", nullable = true, insertable = true, updatable = false) 

        // private Location location; 

        // Caused by: org.hibernate.TransientPropertyValueException: Not-null property references a transient value - transient instance must be saved before current operation :  

        // nz.co.niwa.mintaka.entity.product.ProductHierarchyItem.location -> nz.co.niwa.mintaka.entity.location.Location 

        // ph.setLocation(location); 
    */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "employee_id",
                referencedColumnName = "employee_id",
                unique = true,
                nullable = false,
                insertable = false,
                updatable = false)
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
