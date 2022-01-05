package stockapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.sql.Timestamp;

@Entity
public class Symbol {
    private String id;
    private String symbol;
    private float open;
    private float close;
    private Timestamp cdt;

    public Symbol() {}

    public Symbol(String id, String symbol, float open, float close) {
        this.id = id;
        this.symbol = symbol;
        this.open = open;
        this.close = close;
        this.cdt = new Timestamp(new Date().getTime());
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public Timestamp getCdt() {
        return cdt;
    }

    public void setCdt(Timestamp cdt) {
        this.cdt = cdt;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "id='" + id + '\'' +
                ", symbol='" + symbol + '\'' +
                ", open=" + open +
                ", close=" + close +
                ", cdt=" + cdt +
                '}';
    }
}
