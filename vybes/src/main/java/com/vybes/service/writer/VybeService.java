package com.vybes.service.writer;

import com.vybes.service.writer.entity.Vybe;
import com.vybes.service.writer.repository.VybeRepository;

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
        return vybeRepository.getAllVybes();
    }
}
