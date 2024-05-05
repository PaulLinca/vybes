package com.vybes.service.vybe;

import com.vybes.service.vybe.entity.Vybe;
import com.vybes.service.vybe.repository.VybeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VybeService {
    private final VybeRepository vybeRepository;

    public Vybe postVybe(Vybe vybe) {
        vybeRepository.save(vybe);

        return vybe;
    }

    public List<Vybe> getAllVybes() {
        return vybeRepository.findAll();
    }
}
