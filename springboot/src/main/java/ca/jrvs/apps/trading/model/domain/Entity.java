package ca.jrvs.apps.trading.model.domain;

public interface Entity<ID> {
    ID getTicker();
    void setId(ID id);
}
