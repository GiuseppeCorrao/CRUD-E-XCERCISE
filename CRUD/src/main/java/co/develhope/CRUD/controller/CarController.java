package co.develhope.CRUD.controller;

import co.develhope.CRUD.entities.Car;
import co.develhope.CRUD.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @PostMapping("")
    public Car create(@RequestBody Car car) {
        Car carCreate = carRepository.saveAndFlush(car);
        return carCreate;
    }

    @GetMapping("")
    public Page<Car> getMultiple(@RequestParam(required = false) Optional<Integer> page, @RequestParam(required = false) Optional<Integer> size) {
        if (page.isPresent() && size.isPresent()) {
            Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "modelName"));
            Pageable pageable = PageRequest.of(page.get(), size.get(), sort);
            Page<Car> cars = carRepository.findAll(pageable);
            return cars;
        } else {
            Page<Car> pagecar = null;
            return pagecar;
        }
    }

    @GetMapping("/{id}")
    public Car get(@PathVariable long id) throws Exception {
        if (carRepository.existsById(id)) {
            Car car = carRepository.getReferenceById(id);
            return car;
        } else {
            Car car2 = null;
            return car2;
        }
    }

    @PutMapping("/{id}")
    public Car updateSingle(@PathVariable long id, @RequestBody Car car) {
        car.setId(id);
        if (carRepository.existsById(id)) {
            Car newCar = carRepository.saveAndFlush(car);
            return newCar;
        } else {
            Car carnull = null;
            return carnull;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSingle(@PathVariable long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        } else {
            HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        }
    }
    @DeleteMapping("")
    public void deleteAll(){

        carRepository.deleteAll();
    }


}

