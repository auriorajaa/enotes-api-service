package com.example.enotes.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.example.enotes.entity.FileDetails;
import com.example.enotes.repository.FileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.enotes.dto.CategoryDto;
import com.example.enotes.dto.NotesDto;
import com.example.enotes.entity.Notes;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.CategoryRepository;
import com.example.enotes.repository.NotesRepository;
import com.example.enotes.service.NotesService;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NotesServiceImpl implements NotesService {

  @Autowired
  private NotesRepository notesRepo;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private CategoryRepository categoryRepo;

  @Autowired
  private FileRepository fileRepo;

  @Value("${file.upload.path}")
  private String uploadPath;

  @Override
  public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

    ObjectMapper ob = new ObjectMapper();
    NotesDto notesDto = ob.readValue(notes, NotesDto.class);

    // Validation for category
    checkCategoryExist(notesDto.getCategory());

    Notes notesMap = mapper.map(notesDto, Notes.class);

    FileDetails fileDetails = saveFileDetails(file);

    if (!ObjectUtils.isEmpty(fileDetails)) {
      notesMap.setFileDetails(fileDetails);
    } else {
      notesMap.setFileDetails(null);
    }

    Notes saveNotes = notesRepo.save(notesMap);
    if (!ObjectUtils.isEmpty(saveNotes)) {
      return true;
    }

    return false;
  }

  private FileDetails saveFileDetails(MultipartFile file) throws IOException {

    if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
      String originalFilename = file.getOriginalFilename();
      String extension = FilenameUtils.getExtension(originalFilename);

      List<String> extensionAllow = Arrays.asList("pdf", "png", "jpg", "jpeg", "xlsx", "docx");
      if (!extensionAllow.contains(extension)) {
        throw new IllegalArgumentException("File extension are not allowed (Only .pdf, .png, .jpg, .jpeg, .xlsx, .docx are allowed)");
      }

      String rndString = UUID.randomUUID().toString();
      String uploadFileName = rndString + "." + extension;

      File saveFile = new File(uploadPath);

      if (!saveFile.exists()) {
        saveFile.mkdir();
      }

      // path: enotesapiservice/notes/file.pdf
      String storePath = uploadPath.concat(uploadFileName);

      // upload file
      long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
      if (upload != 0) {
        FileDetails fileDetails = new FileDetails();
        fileDetails.setOriginalFileName(originalFilename);
        fileDetails.setDisplayFileName(getDisplayName(originalFilename));

        fileDetails.setUploadFileName(uploadFileName);
        fileDetails.setFileSize(file.getSize());

        fileDetails.setPath(storePath);

        FileDetails saveFileDetails = fileRepo.save(fileDetails);

        return saveFileDetails;
      }
    }

    return null;
  }

  private String getDisplayName(String originalFilename) {

    String extension = FilenameUtils.getExtension(originalFilename);
    String fileName = FilenameUtils.removeExtension(originalFilename);

    if (fileName.length() > 8) {
      fileName = fileName.substring(0, 7);
    }

    fileName = fileName + "." + extension;

    return fileName;
  }

  private void checkCategoryExist(NotesDto.CategoryDto category) throws Exception {
    categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
  }

  @Override
  public List<NotesDto> getAllNotes() {

    return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
  }

  @Override
  public byte[] downloadFile(FileDetails fileDetails) throws Exception {

    InputStream io = new FileInputStream(fileDetails.getPath());

    return StreamUtils.copyToByteArray(io);
  }

  @Override
  public FileDetails getFileDetails(Integer id) throws Exception {

    FileDetails fileDetails = fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("File not found"));

    return fileDetails;
  }

}
