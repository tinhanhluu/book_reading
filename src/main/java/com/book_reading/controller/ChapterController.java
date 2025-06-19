package com.book_reading.controller;

import com.book_reading.dto.request.ChapterCreationRequest;
import com.book_reading.dto.response.ApiResponse;
import com.book_reading.dto.response.ChapterResponse;
import com.book_reading.service.ChapterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapter")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChapterController {

    ChapterService chapterService;

    @PostMapping
    public ApiResponse<ChapterResponse> createChapter(@RequestBody ChapterCreationRequest request){
        return ApiResponse.<ChapterResponse>builder()
                .message("Create chapter successful")
                .data(chapterService.createChapter(request))
                .build();
    }

    @GetMapping("/{bookId}")
    public ApiResponse<List<ChapterResponse>> getAllChapters(@PathVariable String bookId){
        return ApiResponse.<List<ChapterResponse>>builder()
                .message("Get all chapters successful")
                .data(chapterService.getAllChapters(bookId))
                .build();
    }

    @GetMapping("/{bookId}/{chapterNumer}")
    public ApiResponse<ChapterResponse> getChapter(@PathVariable String bookId, @PathVariable Integer chapterNumer){
        return ApiResponse.<ChapterResponse>builder()
                .message("Get chapter successful")
                .data(chapterService.getChapter(bookId, chapterNumer))
                .build();
    }

}
