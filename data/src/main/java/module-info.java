module data {
    requires lombok;
    requires org.hibernate.commons.annotations;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    
    
    opens pwr.ite.bedrylo.dataModule.model.data;
    opens pwr.ite.bedrylo.dataModule.model.data.enums;
    opens pwr.ite.bedrylo.dataModule.model.request.enums;
    opens pwr.ite.bedrylo.dataModule.model.request;
    
    exports pwr.ite.bedrylo.dataModule.dto;
    exports pwr.ite.bedrylo.dataModule.model.data.enums;
    exports pwr.ite.bedrylo.dataModule.model.request.enums;
    exports pwr.ite.bedrylo.dataModule.model.request;
    exports pwr.ite.bedrylo.dataModule.model.data;

}
