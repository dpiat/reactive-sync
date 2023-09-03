package com.example.testsync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    Scheduler dbScheduler = Schedulers.newSingle("db");

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        user.setNew(true);
        return userRepository.save(user);
    }

    @PutMapping("/{userId}:updateBalance")
    public Mono<User> updateUser(@PathVariable("userId") Long userId, @RequestParam("diff") Double diff) {
        return userRepository.findById(userId)
                .map(user -> {
                    log.info(String.format("current balance - %s, will be - %s", user.getBalance(), user.getBalance() + diff));
                    user.setBalance(user.getBalance() + diff);
                    return user;
                })
                .flatMap(userRepository::save)
                .subscribeOn(dbScheduler);
    }

    @GetMapping("/{userId}")
    public Mono<User> getUser(@PathVariable("userId") Long userId) {
        return userRepository.findById(userId);
    }
}
