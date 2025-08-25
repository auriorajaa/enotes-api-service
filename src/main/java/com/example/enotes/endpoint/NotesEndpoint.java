package com.example.enotes.endpoint;

import com.example.enotes.dto.NotesDto;
import com.example.enotes.dto.NotesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.example.enotes.util.Constants.ROLE_USER;
import static com.example.enotes.util.Constants.ROLE_ADMIN;
import static com.example.enotes.util.Constants.ROLE_ANY;
import static com.example.enotes.util.Constants.DEFAULT_PAGE_NO;
import static com.example.enotes.util.Constants.DEFAULT_PAGE_SIZE;
import static com.example.enotes.util.Constants.DEFAULT_KEY_VALUE;

@Tag(name = "Notes", description = "Notes related API's")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @Operation(summary = "Save notes endpoint")
    @PostMapping(value = "/", consumes = "multipart/form-data")
    @PreAuthorize(ROLE_ANY)
    ResponseEntity<?> saveNotes(
            @RequestParam @Parameter(description = "Json String Notes", required = true,
                    content = @Content(schema = @Schema(implementation = NotesRequest.class)))
            String notes,
            @RequestParam(required = false) MultipartFile file) throws Exception;

    @Operation(summary = "Download attachment file from notes endpoint")
    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ANY)
    ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get all notes by ADMIN endpoint")
    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllNotes();

    @Operation(summary = "Get all user notes endpoint")
    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

    @Operation(summary = "Search notes endpoint")
    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> searchNotes(@RequestParam(name = "key", defaultValue = DEFAULT_KEY_VALUE) String key,
                                         @RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
                                         @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

    @Operation(summary = "Delete notes by ID endpoint")
    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Restore deleted notes by ID endpoint")
    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get recycle bin content endpoint")
    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserRecycleBinNotes() throws Exception;

    @Operation(summary = "Hard delete notes by ID endpoint")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Empty the recycle bin endpoint")
    @DeleteMapping("/empty-recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> emptyUserRecycleBin() throws Exception;

    @Operation(summary = "Add specific notes to favorite by ID endpoint")
    @GetMapping("/favorite/{notesId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> favoriteNotes(@PathVariable Integer notesId) throws Exception;

    @Operation(summary = "Remove notes from favorite by ID endpoint")
    @DeleteMapping("/un-favorite/{favoriteNotesId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> unFavoriteNotes(@PathVariable Integer favoriteNotesId) throws Exception;

    @Operation(summary = "Get all user favorite notes")
    @GetMapping("/favorite-notes")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserFavoriteNotes() throws Exception;

    @Operation(summary = "Duplicate notes by ID endpoint")
    @GetMapping("/copy/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;

}
