package ca.jrvs.apps.trading.model.domain;

public class Position implements Entity<Integer> {
    private Integer account_id;
    private String ticker;
    private Integer position;

    @Override
    public Integer getId() {
        return account_id;
    }

    @Override
    public void setId(Integer account_id) {
        this.account_id = account_id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
