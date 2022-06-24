package com.its.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "board_table")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column (name = "boardTitle", length = 50)
    private String boardTitle;

    @Column (name = "boardWriter", length = 20)
    private String boardWriter;

    @Column (name = "boardPassword", length = 20)
    private String boardPassword;

    @Column (name = "boardContents", length = 500)
    private String boardContents;

    @Column (name = "boardHits")
    private int boardHits;
}
