package com.vybes.dto.response;

import com.vybes.dto.PostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostPageResponse {
    private List<PostDTO> content;
    private int number;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
