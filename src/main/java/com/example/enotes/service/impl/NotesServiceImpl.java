package com.example.enotes.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.NotesDto;
import com.example.enotes.entity.Notes;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.repository.NotesRepository;
import com.example.enotes.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

  @Autowired
  private NotesRepository notesRepo;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private CategoryRepository categoryRepo;

  @Override
  public Boolean saveNotes(NotesDto notesDto) throws Exception {

    // Validation for category
    checkCategoryExist(notesDto.getCategory());

    Notes notes = mapper.map(notesDto, Notes.class);

    Notes saveNotes = notesRepo.save(notes);

    if (!ObjectUtils.isEmpty(saveNotes)) {
      return true;
    }

    return false;
  }

  private void checkCategoryExist(NotesDto.CategoryDto category) throws Exception {
    categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
  }

  @Override
  public List<NotesDto> getAllNotes() {

    return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
  }

}
