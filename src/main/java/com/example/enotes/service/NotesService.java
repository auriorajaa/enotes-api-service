package com.example.enotes.service;

import java.util.List;

import com.example.enotes.dto.NotesDto;

public interface NotesService {

  public Boolean saveNotes(NotesDto notesDto) throws Exception;

  public List<NotesDto> getAllNotes();
}
