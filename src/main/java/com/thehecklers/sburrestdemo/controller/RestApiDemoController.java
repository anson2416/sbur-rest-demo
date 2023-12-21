package com.thehecklers.sburrestdemo.controller;

import com.thehecklers.sburrestdemo.Coffee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffees")
public class RestApiDemoController {
    private List<Coffee> coffees = new ArrayList<Coffee>();
    public RestApiDemoController() {
        coffees.addAll(List.of(
                new Coffee("Cafe Cereza"),
                new Coffee("Cafe Ganador"),
                new Coffee("Cafe Lareno"),
                new Coffee("Cafe Tres Pontas")
                ));
    }

//    @RequestMapping(value= "/coffees", method = RequestMethod.GET)
    @GetMapping
    Iterable<Coffee> getCoffees() {
        return coffees;
    }

    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id) {
        for (Coffee c: coffees) {
            if (c.getId().equals(id)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

   @PostMapping
   Coffee postCoffee(@RequestBody Coffee coffee) {
//        for (Coffee c : coffees) {
//            if (c.getId().equals(coffee.getId())) {
//                return new ResponseEntity(coffee, HttpStatus.IM_USED);
//            }
//        }

        coffees.add(coffee);
        return coffee;
   }

   @PutMapping("/{id}")
   ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        int coffeeIndex = -1;

        for (Coffee c : coffees) {
            if (c.getId().equals(id) ) {
                // Update the existing coffee
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }

//        return (coffeeIndex == -1) ? postCoffee(coffee) : coffee;
       return (coffeeIndex == -1) ?
               new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED) :
               new ResponseEntity<>(coffee, HttpStatus.OK);

   }


   @DeleteMapping("/{id}")
   void deleteCoffee(@PathVariable String id) {
        coffees.removeIf(c -> c.getId().equals(id));
   }


}
