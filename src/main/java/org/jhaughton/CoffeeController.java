package org.jhaughton;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    private Random randomGenerator = new Random();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Coffee[]> getAllCoffeeTypes() {
        return new ResponseEntity<>(Coffee.values(), HttpStatus.OK);
    }

    @RequestMapping(value = "{coffeeType}", method = RequestMethod.GET)
    public ResponseEntity<String> getSpecificCoffeeType(@PathVariable String coffeeType) {
        Coffee coffee = Coffee.valueOf(coffeeType.toUpperCase());
        return new ResponseEntity<>(coffee.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "{coffeeType}", method = RequestMethod.POST)
    public ResponseEntity<String> setSpecificCoffeeType(@RequestBody @NotNull int preference, @PathVariable String coffeeType) {
        Coffee coffee = Coffee.valueOf(coffeeType.toUpperCase());
        coffee.setPreference(preference);
        return new ResponseEntity<>(coffee.toString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/preference", method = RequestMethod.GET)
    public ResponseEntity<String> getCoffeesWithPreferences() {
        JSONObject response = new JSONObject();
        for (Coffee coffee : Coffee.values()) {
            response.put("name", coffee.name());
            response.put("preference", coffee.getPreference());
        }

        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/choose", method = RequestMethod.GET)
    public ResponseEntity<Coffee> getRandomCoffee() {
        ArrayList<Coffee> coffeeChoices = new ArrayList<>();

        for (Coffee coffee : Coffee.values()) {
            int preference = coffee.getPreference();
            for (int i = preference; i > 0; i--) {
                coffeeChoices.add(coffee);
            }
        }

        Collections.shuffle(coffeeChoices);
        int choice = randomGenerator.nextInt(coffeeChoices.size());
        return new ResponseEntity<>(coffeeChoices.get(choice), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleNoEnum() {
        return new ResponseEntity<>("No such coffee type.", HttpStatus.BAD_REQUEST);
    }




}
