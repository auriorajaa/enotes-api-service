package com.example.enotes.service;

import java.util.List;

import com.example.enotes.dto.FavoriteNotesDto;
import com.example.enotes.dto.NotesDto;
import com.example.enotes.dto.NotesResponse;
import com.example.enotes.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

public interface NotesService {

  public Boolean saveNotes(String notes, MultipartFile file) throws Exception;

  public List<NotesDto> getAllNotes();

  public byte[] downloadFile(FileDetails fileDetails) throws Exception;

  public FileDetails getFileDetails(Integer id) throws Exception;

  public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

  public void softDeleteNotes(Integer id) throws Exception;

  public void restoreNotes(Integer id) throws Exception;

  public List<NotesDto> getUserRecycleBinNotes(Integer userId);

  public void hardDeleteNotes(Integer id) throws Exception;

  public void emptyRecycleBin(int userId);

  public void favoriteNotes(Integer notesId) throws Exception;

  public void unFavoriteNotes(Integer favoriteNotesId) throws Exception;

  public List<FavoriteNotesDto> getUserFavoriteNotes() throws Exception;

  public Boolean copyNotes(Integer id) throws Exception;
}
