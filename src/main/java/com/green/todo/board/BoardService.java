package com.green.todo.board;

import com.green.todo.board.model.req.CreateBoardFileDto;
import com.green.todo.board.model.req.CreateBoardReq;
import com.green.todo.board.model.res.GetBoardRes;
import com.green.todo.board.module.DateTimeValidateUtils;
import com.green.todo.board.module.EtcModule;
import com.green.todo.common.CustomFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;
    private final CustomFileUtils customFileUtils;

    @Transactional
    public long createBoard(CreateBoardReq p, List<MultipartFile> files) throws Exception {
        List<String> uploadedFiles = new ArrayList<>();


        /*----------------------------파일, 태그 제외한 내용 검증 및 저장시작------------------------------*/
        try {
            EtcModule.validateCreateBoardReq(p);
            LocalDate startDate = DateTimeValidateUtils.dateValidator(p.getStartDay());
            LocalDate endDate = DateTimeValidateUtils.dateValidator(p.getDDay());
            if (startDate.isAfter(endDate)) throw new RuntimeException("마감일은 시작일보다 빠를 수 없습니다.");
            DateTimeValidateUtils.timeValidator(p.getDeadLine());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("유효성 검사 통과 실패");
        }

        long result = mapper.createBoard(p);
        if (result == 0) {
            throw new RuntimeException("잘못된 결과값 입니다.");
        }


        /*----------------------------파일 로컬 및 db에 저장 시작------------------------------*/
        CreateBoardFileDto dto = CreateBoardFileDto.builder().boardId(p.getBoardId()).build();

        if (files != null && !files.isEmpty()) {
            try {
                String path = String.format("board/%d", p.getBoardId());
                customFileUtils.makeFolder(path);

                for (MultipartFile file : files) {
                    String newName = customFileUtils.makeRandomFileName(file);
                    dto.getFileNames().add(newName);
                    String target = String.format("%s/%s", path, newName);
                    customFileUtils.transferTo(file, target);
                    uploadedFiles.add(target); // 경로 저장 수정

//                    String nameState = customFileUtils.getExtOnlyAlpha(newName);
//                    System.out.println(nameState);
//                    if (nameState.equals("png") || nameState.equals("jpeg") || nameState.equals("jpg") || nameState.equals("gif") || nameState.equals("webp")) {
//                        dto.getState().add(1);
//                    } else {
//                        dto.getState().add(2);
//                    }
                }
            } catch (Exception e) {
                customFileUtils.cleanupFiles(uploadedFiles);
                throw new RuntimeException("업로드 안된다고~!~!", e);
            }

            try {
                mapper.createBoardFiles(dto);
            } catch (Exception e) {
                customFileUtils.cleanupFiles(uploadedFiles);
                throw new RuntimeException("파일이 db에 저장이 안된다고~!~!", e);
            }
        }

        
        /*----------------------------board_tag 저장 시작------------------------------*/


        return result;
    }

    public List<GetBoardRes> getBoardList(int month) throws Exception {

        if (1 > month || month > 12) {
            throw new RuntimeException("올바르지 않은 Month");
        }

        List<GetBoardRes> boardListByMonth = mapper.getBoardListByMonth(month);

        for (GetBoardRes oneBoard : boardListByMonth) {
            List<String> files = mapper.getBoardFiles(oneBoard.getBoardId());
            oneBoard.setFiles(files);
        }

        return boardListByMonth;
    }
}
