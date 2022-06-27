package com.its.board.service;

import com.its.board.dto.BoardDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Long save(BoardDTO boardDTO) {
       BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
      Long saveId = boardRepository.save(boardEntity).getId();
//      Long saveId = boardRepository.save(BoardEntity.toSaveEntity(boardDTO)).getId();
      return saveId;
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toboardDTO(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional
    public BoardDTO findById(Long id) {
        //  조회수 처리
        // native sql: update board_table set boardHits=boardHits+1 where id=?
        boardRepository.boardHits(id);

        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()) {
//            BoardEntity boardEntity = optionalBoardEntity.get();
//            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
//            return boardDTO;
            return BoardDTO.toBoardDTO((optionalBoardEntity.get()));
        }else {
            return null;
        }
    }


    public void update(BoardDTO boardDTO) {
        boardRepository.save(BoardEntity.toUpdateEntity(boardDTO));
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
