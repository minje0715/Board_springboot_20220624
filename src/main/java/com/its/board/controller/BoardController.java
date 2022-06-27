package com.its.board.controller;

import com.its.board.dto.BoardDTO;
import com.its.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService; // 생성자 주입 방식

    @GetMapping("/save-form")
    public String saveForm() {
        return "boardPages/save";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) {
       Long id = boardService.save(boardDTO);
        return "redirect:/board/" +id;
    }
    @GetMapping("/")
    public String findAll(Model model) {
       List<BoardDTO> boardDTOList = boardService.findAll();
       model.addAttribute("boardList", boardDTOList);
       return "boardPages/findAll";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/detail";
    }

    @GetMapping("/update-form/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
       BoardDTO boardDTO = boardService.findById(id);
       model.addAttribute("board", boardDTO);
       return "boardPages/update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO) {
       boardService.update(boardDTO);
        return "redirect:/board/" + boardDTO.getId();
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
    boardService.delete(id);
    return "redirect:/board/";
    }

}
