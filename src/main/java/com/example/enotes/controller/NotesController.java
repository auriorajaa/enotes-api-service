package com.example.enotes.controller;

import java.util.List;

import com.example.enotes.dto.NotesResponse;
import com.example.enotes.entity.FileDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.example.enotes.dto.NotesDto;
import com.example.enotes.service.NotesService;
import com.example.enotes.util.CommonUtil;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

  @Autowired
  private NotesService notesService;

  @PostMapping("/")
  public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception {
    Boolean saveNotes = notesService.saveNotes(notes, file);

    if (saveNotes) {
      return CommonUtil.createBuildResponseMessage("Notes successfully created.", HttpStatus.CREATED);
    }

    return CommonUtil.createErrorResponseMessage("Notes failed to create.", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @GetMapping("/download/{id}")
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
  public ResponseEntity<?> getAllNotes() {
    List<NotesDto> notes = notesService.getAllNotes();

    if (CollectionUtils.isEmpty(notes)) {
      return ResponseEntity.noContent().build();
    }

    return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
  }

  @GetMapping("/user-notes")
  public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
    Integer userId = 2;
    NotesResponse notes = notesService.getAllNotesByUser(userId, pageNo, pageSize);

//    if (CollectionUtils.isEmpty(notes)) {
//      return ResponseEntity.noContent().build();
//    }

    return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
  }

  @GetMapping("/delete/{id}")
  public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {

    notesService.softDeleteNotes(id);

    return CommonUtil.createBuildResponseMessage("Notes successfully deleted.", HttpStatus.OK);
  }

  @GetMapping("/restore/{id}")
  public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {

    notesService.restoreNotes(id);

    return CommonUtil.createBuildResponseMessage("Notes successfully restored.", HttpStatus.OK);
  }

  @GetMapping("/recycle-bin")
  public ResponseEntity<?> getUserRecycleBinNotes() throws Exception {

    Integer userId = 2;

    List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);

    if (CollectionUtils.isEmpty(notes)) {
      return CommonUtil.createBuildResponseMessage("No notes found in recycle bin.", HttpStatus.OK);
    }

    return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
  }
}
