package com.example.enotes.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteNotesDto {

 private Integer id;

 private NotesDto notes;

 private Integer userId;
}
