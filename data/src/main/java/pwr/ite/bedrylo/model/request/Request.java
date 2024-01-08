package pwr.ite.bedrylo.model.request;

import lombok.Data;
import java.io.Serializable;

@Data
public class Request implements Serializable{
    private Enum<?> action;
    private Object data;
    
    public Request(Enum<?> action, Object data){
        this.action = action;
        this.data = data;
    }
}
