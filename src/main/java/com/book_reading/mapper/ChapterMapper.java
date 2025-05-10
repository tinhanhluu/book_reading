package com.book_reading.mapper;

import com.book_reading.dto.request.ChapterCreationRequest;
import com.book_reading.dto.response.ChapterResponse;
import com.book_reading.entity.Chapter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    Chapter toChapter(ChapterCreationRequest request);
    ChapterResponse toChapterResponse(Chapter chapter);

}
