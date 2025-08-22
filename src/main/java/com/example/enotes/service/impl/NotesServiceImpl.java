package com.example.enotes.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.enotes.dto.FavoriteNotesDto;
import com.example.enotes.dto.NotesResponse;
import com.example.enotes.entity.FavoriteNotes;
import com.example.enotes.entity.FileDetails;
import com.example.enotes.entity.User;
import com.example.enotes.repository.FavoriteNotesRepository;
import com.example.enotes.repository.FileRepository;
import com.example.enotes.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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

  @Autowired
  private FavoriteNotesRepository favoriteNotesRepo;

  @Value("${file.upload.path}")
  private String uploadPath;

  @Override
  public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

    ObjectMapper ob = new ObjectMapper();
    NotesDto notesDto = ob.readValue(notes, NotesDto.class);

    notesDto.setDeleted(false);
    notesDto.setDeletedOn(null);

    // Update notes if id is not null (id is given)
    if (!ObjectUtils.isEmpty(notesDto.getId())) {
      updateNotes(notesDto, file);
    }

    // Validation for category
    checkCategoryExist(notesDto.getCategory());

    Notes notesMap = mapper.map(notesDto, Notes.class);

    FileDetails fileDetails = saveFileDetails(file);

    if (!ObjectUtils.isEmpty(fileDetails)) {
      notesMap.setFileDetails(fileDetails);
    } else {
      if (ObjectUtils.isEmpty(notesDto.getId())) {
        notesMap.setFileDetails(null);
      }
    }

    Notes saveNotes = notesRepo.save(notesMap);
    if (!ObjectUtils.isEmpty(saveNotes)) {
      return true;
    }

    return false;
  }

  private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
    Notes existNotes = notesRepo.findById(notesDto.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Notes not found"));

    // Update notes if user not choosing any attachment file
    if (ObjectUtils.isEmpty(file)) {
      notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(), NotesDto.FilesDto.class));
    }
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

  @Override
  public NotesResponse getAllNotesByUser(Integer pageNo, Integer pageSize) {

    Integer userId = CommonUtil.getLoggedInUser().getId();

    Pageable pageable = PageRequest.of(pageNo,pageSize);
    Page<Notes> pageNotes = notesRepo.findByCreatedByAndIsDeletedFalse(userId, pageable);

    List<NotesDto> notesDto = pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();

    NotesResponse notes = NotesResponse.builder()
            .notes(notesDto)
            .pageNo(pageNotes.getNumber())
            .pageSize(pageNotes.getSize())
            .totalElements(pageNotes.getTotalElements())
            .totalPages(pageNotes.getTotalPages())
            .isFirst(pageNotes.isFirst())
            .isLast(pageNotes.isLast())
            .build();

    return notes;
  }

    @Override
    public NotesResponse getAllNotesByUserSearch(Integer pageNo, Integer pageSize, String keyword) {
        Integer userId = CommonUtil.getLoggedInUser().getId();

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Notes> pageNotes = notesRepo.searchNotes(keyword, userId, pageable);

        List<NotesDto> notesDto = pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();

        NotesResponse notes = NotesResponse.builder()
                .notes(notesDto)
                .pageNo(pageNotes.getNumber())
                .pageSize(pageNotes.getSize())
                .totalElements(pageNotes.getTotalElements())
                .totalPages(pageNotes.getTotalPages())
                .isFirst(pageNotes.isFirst())
                .isLast(pageNotes.isLast())
                .build();

        return notes;    }

    @Override
  public void softDeleteNotes(Integer id) throws Exception {
    Notes notes = notesRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notes not found"));

    notes.setDeleted(true);
    notes.setDeletedOn(LocalDateTime.now());
    notesRepo.save(notes);
  }

  @Override
  public void restoreNotes(Integer id) throws Exception {
    Notes notes = notesRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notes not found"));

    notes.setDeleted(false);
    notes.setDeletedOn(null);
    notesRepo.save(notes);
  }

  @Override
  public List<NotesDto> getUserRecycleBinNotes() {
    Integer userId = CommonUtil.getLoggedInUser().getId();
    List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);
    List<NotesDto> notesDtoList = recycleNotes.stream().map(notes -> mapper.map(notes, NotesDto.class)).toList();

    return notesDtoList;
  }

  @Override
  public void hardDeleteNotes(Integer id) throws Exception {
    Notes notes = notesRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notes not found"));

    if (notes.isDeleted()) {
      notesRepo.delete(notes);
    } else {
      throw new IllegalArgumentException("Notes is not in recycle bin");
    }
  }

  @Override
  public void emptyRecycleBin() {
    Integer userId = CommonUtil.getLoggedInUser().getId();

    List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);

    if (!CollectionUtils.isEmpty(recycleNotes)) {
      notesRepo.deleteAll(recycleNotes);
    }
  }

  @Override
  public void favoriteNotes(Integer notesId) throws Exception{
    Integer userId = CommonUtil.getLoggedInUser().getId();

    Notes notes = notesRepo.findById(notesId)
            .orElseThrow(() -> new ResourceNotFoundException("Notes not found"));

    FavoriteNotes favoriteNotes = FavoriteNotes.builder()
            .notes(notes)
            .userId(userId)
            .build();

    favoriteNotesRepo.save(favoriteNotes);
  }

  @Override
  public void unFavoriteNotes(Integer favoriteNotesId) throws Exception{
    FavoriteNotes favoriteNotes = favoriteNotesRepo.findById(favoriteNotesId)
            .orElseThrow(() -> new ResourceNotFoundException("Favorite notes not found"));

    favoriteNotesRepo.delete(favoriteNotes);
  }

  @Override
  public List<FavoriteNotesDto> getUserFavoriteNotes() throws Exception {
      Integer userId = CommonUtil.getLoggedInUser().getId();

    List<FavoriteNotes> favoriteNotes = favoriteNotesRepo.findByUserId(userId);

    return favoriteNotes.stream().map(favNotes -> mapper.map(favNotes, FavoriteNotesDto.class)).toList();
  }

  @Override
  public Boolean copyNotes(Integer id) throws Exception {
    Notes notes = notesRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Notes not found"));

   Notes copyNotes = Notes.builder()
           .title(notes.getTitle())
           .description(notes.getDescription())
           .category(notes.getCategory())
           .isDeleted(false)
           .fileDetails(null)
           .build();

   // TODO: Need to check user validation
   Notes saveCopyNotes = notesRepo.save(copyNotes);

   if (!ObjectUtils.isEmpty(saveCopyNotes)) {
     return true;
   }

   return false;
  }

}
