package org.jhaughton;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

public enum Coffee {
    LATTE(1),
    CAPPUCCINO(1),
    BLACK_COFFEE(1),
    TOFFEE_LATTE(1),
    ESPRESSO(1),
    MOCHA(1);

    @JsonProperty
    private int preference;

    Coffee(int preference) {
        this.preference = preference;
    }

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    @Override
    public String toString() {
        JSONObject coffee = new JSONObject();
        coffee.put("name", this.name());
        coffee.put("preference", this.getPreference());

        return coffee.toString();
    }
}
