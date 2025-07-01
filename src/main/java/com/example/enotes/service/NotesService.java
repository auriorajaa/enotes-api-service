package com.example.enotes.service;

import java.util.List;

import com.example.enotes.dto.NotesDto;
import org.springframework.web.multipart.MultipartFile;

public interface NotesService {

  public Boolean saveNotes(String notes, MultipartFile file) throws Exception;

  public List<NotesDto> getAllNotes();
}
