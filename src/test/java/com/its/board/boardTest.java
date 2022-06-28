package com.its.board;

import com.its.board.common.PagingConst;
import com.its.board.dto.BoardDTO;
import com.its.board.dto.MemberDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.entity.MemberEntity;
import com.its.board.repository.BoardRepository;
import com.its.board.repository.MemberRepository;
import com.its.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
public class boardTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public BoardDTO newBoard(int i){
        BoardDTO member =
                new BoardDTO("테스트제목" + i, "테스트용작성자" + i, "테스트용비밀번호" + i, "테스트용내용" + i, 0);
        return member;
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void newMember() {
        MemberDTO memberDTO = new MemberDTO("email", "pw1", "name1");
        memberRepository.save(MemberEntity.toSaveEntity(memberDTO));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원 게시글 연관관계 테스트")
    public void memberBoardSaveTest() {
        BoardDTO boardDTO = newBoard(1);// 저장할 게시글 객체
        // 회원 엔티티 객체를 같이 줘야하니까 위에서 저장한 이메일값으로 회원 엔티티 조회
        MemberEntity memberEntity = memberRepository.findByMemberEmail("email").get();
        // 게시글 객체와 회원 엔티티로 boardEntity 객체 생성
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, memberEntity);
        //저장 수행
//        boardRepository.save(boardEntity);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원 게시글 연관관계 테스트")
    public void memberBoardFindByIdTest() {
        // 위에서 저장한 테이블 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(1L);
        if(optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            System.out.println("boardEntity.getId() = " + boardEntity.getId());
            System.out.println("boardEntity.getBoardTitle() = " + boardEntity.getBoardTitle());
            // 객체 그ㅐ프 탐색
            System.out.println("boardEntity.getMemberEntity().getMemberName() = " + boardEntity.getMemberEntity().getMemberName());
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void deleteTest() {
//       게시글 삭제
//        boardRepository.deleteById(1L);
        memberRepository.deleteById(1L);
    }

    @Test
    @Transactional
    @DisplayName("페이징테스트")
    public void pagingTest() {
        int page = 2;
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Page 객체가 제공해주는 메서드 확인
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막페이지인지 여부

        Page<BoardDTO> boardList = boardEntities.map(
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime()
                ));

        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막페이지인지 여부

    }
}
