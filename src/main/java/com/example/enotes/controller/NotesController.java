package com.example.enotes.controller;

import java.util.List;

import com.example.enotes.dto.FavoriteNotesDto;
import com.example.enotes.dto.NotesResponse;
import com.example.enotes.entity.FileDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.example.enotes.dto.NotesDto;
import com.example.enotes.service.NotesService;
import com.example.enotes.util.CommonUtil;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

  @Autowired
  private NotesService notesService;

  @PostMapping("/")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception {
    Boolean saveNotes = notesService.saveNotes(notes, file);

    if (saveNotes) {
      return CommonUtil.createBuildResponseMessage("Notes successfully created.", HttpStatus.CREATED);
    }

    return CommonUtil.createErrorResponseMessage("Notes failed to create.", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @GetMapping("/download/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {

    FileDetails fileDetails = notesService.getFileDetails(id);
    byte[] data = notesService.downloadFile(fileDetails);

    HttpHeaders headers = new HttpHeaders();
    String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
    headers.setContentType(MediaType.parseMediaType(contentType));
    headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

    return ResponseEntity.ok().headers(headers).body(data);
  }


  @GetMapping("/")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAllNotes() {
    List<NotesDto> notes = notesService.getAllNotes();

    if (CollectionUtils.isEmpty(notes)) {
      return ResponseEntity.noContent().build();
    }

    return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
  }

  @GetMapping("/user-notes")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
    NotesResponse notes = notesService.getAllNotesByUser(pageNo, pageSize);

//    if (CollectionUtils.isEmpty(notes)) {
//      return ResponseEntity.noContent().build();
//    }

    return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
  }

  @GetMapping("/search")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> searchNotes(@RequestParam(name = "key", defaultValue = "") String key,
                                       @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
      NotesResponse notes = notesService.getAllNotesByUserSearch(pageNo, pageSize, key);

      return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
  }

  @GetMapping("/delete/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {

    notesService.softDeleteNotes(id);

    return CommonUtil.createBuildResponseMessage("Notes successfully deleted.", HttpStatus.OK);
  }

  @GetMapping("/restore/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {

    notesService.restoreNotes(id);

    return CommonUtil.createBuildResponseMessage("Notes successfully restored.", HttpStatus.OK);
  }

  @GetMapping("/recycle-bin")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getUserRecycleBinNotes() throws Exception {
    List<NotesDto> notes = notesService.getUserRecycleBinNotes();

    if (CollectionUtils.isEmpty(notes)) {
      return CommonUtil.createBuildResponseMessage("No notes found in recycle bin.", HttpStatus.OK);
    }

    return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {

    notesService.hardDeleteNotes(id);

    return CommonUtil.createBuildResponseMessage("Notes successfully deleted.", HttpStatus.OK);
  }

  @DeleteMapping("/empty-recycle-bin")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> emptyUserRecycleBin() throws Exception {

    notesService.emptyRecycleBin();

    return CommonUtil.createBuildResponseMessage("Recycle bin successfully emptied.", HttpStatus.OK);
  }

  @GetMapping("/favorite/{notesId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> favoriteNotes(@PathVariable Integer notesId) throws Exception {
    notesService.favoriteNotes(notesId);

    return CommonUtil.createBuildResponseMessage("Notes successfully added to favorite.", HttpStatus.CREATED);
  }

  @DeleteMapping("/un-favorite/{favoriteNotesId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> unFavoriteNotes(@PathVariable Integer favoriteNotesId) throws Exception {
    notesService.unFavoriteNotes(favoriteNotesId);

    return CommonUtil.createBuildResponseMessage("Notes successfully removed from favorite.", HttpStatus.OK);
  }

  @GetMapping("/favorite-notes")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> getUserFavoriteNotes() throws Exception {
    List<FavoriteNotesDto> userFavoriteNotes = notesService.getUserFavoriteNotes();

    if (CollectionUtils.isEmpty(userFavoriteNotes)) {
      return ResponseEntity.noContent().build();
    }

    return CommonUtil.createBuildResponse(userFavoriteNotes,HttpStatus.OK);

  }

  @GetMapping("/copy/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception {
    Boolean copyNotes = notesService.copyNotes(id);

    if (copyNotes) {
      return CommonUtil.createBuildResponseMessage("Notes successfully copied.", HttpStatus.CREATED);
    }

    return CommonUtil.createErrorResponseMessage("Notes failed to copy.", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
