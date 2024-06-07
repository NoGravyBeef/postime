package com.green.todo.board;

import com.green.todo.board.model.req.*;
import com.green.todo.board.model.res.*;
import com.green.todo.board.module.DateTimeValidateUtils;
import com.green.todo.board.module.EtcModule;
import com.green.todo.calendar.module.CountLengthUtil;
import com.green.todo.common.CustomFileUtils;
import com.green.todo.notice.NoticeService;
import com.green.todo.notice.model.req.NoticeReq;
import com.green.todo.tag.TagMapper;
import com.green.todo.tag.TagService;
import com.green.todo.tag.model.TagEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final TagService tagService;
    private final TagMapper tagMapper;
    private final NoticeService noticeService;

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    @Transactional
    public long createBoard(CreateBoardReq p, List<MultipartFile> files) {
        List<String> uploadedFiles = new ArrayList<>();


        /*----------------------------파일, 태그 제외한 내용 검증 및 저장시작------------------------------*/

        if (CountLengthUtil.countLength(p.getTitle()) > 20 || 1 > CountLengthUtil.countLength(p.getTitle())) {
            throw new RuntimeException("제목 길이 맞춰주세요~!~!(20자)");
        }

        if (1 > CountLengthUtil.countLength(p.getContent()) || CountLengthUtil.countLength(p.getContent()) > 1000) {
            throw new RuntimeException("본문 길이 맞춰주세요~!~!(1000자)");
        }

        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            EtcModule.validateCreateBoardReq(p);

            LocalDate startDate = DateTimeValidateUtils.dateValidator(p.getStartDay());
            LocalDate endDate = DateTimeValidateUtils.dateValidator(p.getDDay());
            LocalTime deadLine = DateTimeValidateUtils.timeValidator(p.getDeadLine());

            startDateTime = startDate.atTime(LocalTime.of(0,0,0));
            endDateTime = endDate.atTime(deadLine);

        } catch (Exception e) {
            throw new RuntimeException("유효성 검사 통과 실패~!~!");
        }

        if (startDateTime.isAfter(endDateTime)) {
            throw new RuntimeException("마감일은 시작일보다 빠를 수 없습니다~!~!");
        }

        long result = mapper.createBoard(p);
        if (result == 0) {
            throw new RuntimeException("잘못된 결과값 입니다~!~!");
        }


        /*----------------------------파일 로컬 및 db에 저장 시작------------------------------*/
        CreateBoardFileDto dto = CreateBoardFileDto.builder().boardId(p.getBoardId()).build();

        if (files != null && !files.isEmpty()) {
            try {
                String path = String.format("calendar/%d/board/%d", p.getCalendarId(), p.getBoardId());
                customFileUtils.makeFolder(path);

                for (MultipartFile file : files) {
                    String newName = customFileUtils.makeRandomFileName(file);
                    dto.getFileNames().add(newName);
                    String target = String.format("%s/%s", path, newName);
                    customFileUtils.transferTo(file, target);
                    uploadedFiles.add(target); // 경로 저장 수정
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

        
        /*----------------------------태그 저장 및 등록 시작------------------------------*/

        //이거 김민지가 한거
        int tagResult = tagService.tagPost(p.getExistTag(), p.getNotExistTag(), p.getBoardId());

        /*----------------------------알림 저장 및 등록 시작------------------------------*/

        NoticeReq req = new NoticeReq(p.getCalendarId(), p.getSignedUserId());
        //ㅣ이거 김민지가 한거
        noticeService.newBoardNotice(req);

        return p.getBoardId();
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public GetBoardRes getBoardInfo(Long boardId) {
        if (boardId == null) {
            throw new RuntimeException("보드id를 넣으셔야 합니다~!~!");
        }

        GetBoardRes boardInfo;
        try {
            boardInfo = mapper.getBoardInfoByBoardId(boardId);

            List<FileRes> files = mapper.getBoardFiles(boardInfo.getBoardId());
            boardInfo.setFiles(files);

            List<TagEntity> tags = tagMapper.getTagsByBoardId(boardId);
            boardInfo.setTags(tags);
        } catch (Exception e) {
            throw new RuntimeException("board정보 불러오기 실패~!~!");
        }

        return boardInfo;
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public List<GetBoardMiniRes> getBoardMiniList(Long userId, Integer month){

        if (userId == null || month == null) {
            throw new RuntimeException("모든 항목은 반드시 필요합니다.");
        }

        List<GetBoardMiniRes> boardListByUserId;
        try {
            boardListByUserId = mapper.getBoardMiniListByUserIdAndMonth(userId, month);
        } catch (Exception e) {
            throw new RuntimeException("board정보 불러오기 실패~!~!");
        }

        return boardListByUserId;
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public List<GetBoardMiniRes> getBoardDoneList(Long userId) {

        if (userId == null) {
            throw new RuntimeException("유저 id를 입력하셔야합니다.");
        }

        List<GetBoardMiniRes> doneBoardList = null;
        try {
            doneBoardList = mapper.getBoardMiniByState(userId, 2);
        } catch (Exception e) {
            throw new RuntimeException("완료 보드 불러오기 쿼링 이슈~!~!");
        }

        return doneBoardList;
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public List<GetBoardMiniRes> getBoardDeletedList(Long userId) {

        if (userId == null) {
            throw new RuntimeException("유저 id를 입력하셔야합니다.");
        }

        List<GetBoardMiniRes> doneBoardList = null;
        try {
            doneBoardList = mapper.getBoardMiniByState(userId, 3);
        } catch (Exception e) {
            throw new RuntimeException("삭제 보드 불러오기 쿼링 이슈~!~!");
        }

        return doneBoardList;
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public List<GetBoardMiniRes> getBoardSearchList(String searchWord, Long signedUserId) {

        if (signedUserId == null) {
            throw new RuntimeException("유저 id를 입력하셔야합니다.");
        }

        if (searchWord == null || searchWord.isEmpty()) {
            throw new RuntimeException("검색어를 입력하셔야합니다.");
        }

        List<GetBoardMiniRes> searchBoardList = null;
        try {
            searchBoardList = mapper.getBoardSearchList(searchWord, signedUserId);
        } catch (Exception e) {
            throw new RuntimeException("검색한 보드 불러오기 쿼링 이슈~!~!");
        }

        return searchBoardList;
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public TodoListRes getBoardTodoList(Long userId) {
        if (userId == null) {
            throw new RuntimeException("유저 id를 입력하셔야합니다.");
        }

        TodoListRes todoListRes = new TodoListRes();
        try {
            List<GetBoardTodoRes> untilTodayBoard = mapper.selectBoardsByUserIdForToday(userId);
            List<GetBoardTodoRes> untilThisMonthBoard = mapper.selectBoardsByUserIdForCurrentMonth(userId);
            List<GetBoardTodoRes> untilNextMonthBoard = mapper.selectBoardsByUserIdForNextMonth(userId);

            todoListRes.setUntilTodayBoard(untilTodayBoard);
            todoListRes.setUntilThisMonthBoard(untilThisMonthBoard);
            todoListRes.setUntilNextMonthBoard(untilNextMonthBoard);
        } catch (Exception e) {
            throw new RuntimeException("할 일 목록을 불러오는 도중 오류가 발생했습니다.");
        }

        return todoListRes;
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////



    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public long updateBoard(UpdateBoardReq p) {
        if (p.getDDay() == null && p.getStartDay() == null && p.getTitle() == null
        && p.getContent() == null && p.getDeadLine() == null) {
            throw new RuntimeException("수정할 사안이 없습니다.");
        }

        if (p.getBoardId() == null) {
            throw new RuntimeException("보드id는 무조건 넣으셔야 합니다.");
        }

        BoardEntity boardEntity;
        try {
            boardEntity = mapper.getBoardByBoardId(p.getBoardId());
        } catch (Exception e) {
            throw new RuntimeException("기존 보드 정보 가져오기 쿼링 이슈~!~!");
        }
        if (boardEntity == null) {
            throw new RuntimeException("수정할 보드가 존재하지 않습니다~!~!");
        }

        if (p.getTitle() != null) {
            if (1 >= CountLengthUtil.countLength(p.getTitle()) || CountLengthUtil.countLength(p.getTitle()) >= 20) {
                throw new RuntimeException("제목 20자 이내로 맞춰주세요~!");
            }
        }

        if (p.getContent() != null) {
            if (1 >= CountLengthUtil.countLength(p.getContent()) || CountLengthUtil.countLength(p.getContent()) >= 1000) {
                throw new RuntimeException("제목 20자 이내로 맞춰주세요~!");
            }
        }

        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        LocalDate startDate;
        LocalDate endDate;
        LocalTime deadLine;
        try {
            if (p.getStartDay() != null) {
                startDate = DateTimeValidateUtils.dateValidator(p.getStartDay());
            } else {
                startDate = DateTimeValidateUtils.dateValidator(boardEntity.getStartDay());
            }

            if (p.getDDay() != null) {
                endDate = DateTimeValidateUtils.dateValidator(p.getDDay());
            } else {
                endDate = DateTimeValidateUtils.dateValidator(boardEntity.getDDay());
            }

            if (p.getDeadLine() != null) {
                deadLine = DateTimeValidateUtils.timeValidator(p.getDeadLine());
            } else {
                deadLine = DateTimeValidateUtils.timeValidator(boardEntity.getDeadLine());
            }

            startDateTime = startDate.atTime(0,0,0);
            endDateTime = endDate.atTime(deadLine);
        } catch (Exception e) {
            throw new RuntimeException("날짜 및 시간 입력 양식 틀림~!~!");
        }

        if (startDateTime.isAfter(endDateTime)) {
            throw new RuntimeException("마감일은 시작일보다 빠를 수 없습니다.");
        }

        //이거 김민지가 한거
        int tagResult = tagService.tagDelete(p.getDeltagList(), p.getBoardId());

        int result;
        try {
            result = mapper.updateBoard(p);
        } catch (Exception e) {
            throw new RuntimeException("일정 업데이트 쿼링 이슈~!~!");
        }

        return p.getBoardId();

    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public long updateBoardState(UpdateBoardStateReq p) {
        if (p.getBoardId() == null || p.getState() == null) {
            throw new RuntimeException("모든 항목 무조건 넣으셔야 합니다~!~!");
        }
        if (p.getState() < 1 || p.getState() > 3) {
            throw new RuntimeException("state값 제대로 넣으셔야해요~!~!");
        }

        int result;
        try {
            result = mapper.updateBoardState(p);
        } catch (Exception e) {
            throw new RuntimeException("state 업데이트 쿼링 이슈~!~!");
        }

        return p.getBoardId();
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public long updateBoardDnD(UpdateBoardDnDReq p) {
        if (p.getBoardId() == null || p.getDDay() == null || p.getStartDay() == null) {
            throw new RuntimeException("모든 항목을 반드시 채우셔야 합니다~!~!");
        }

        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            LocalDate startDate = DateTimeValidateUtils.dateValidator(p.getStartDay());
            LocalDate endDate = DateTimeValidateUtils.dateValidator(p.getDDay());

            startDateTime = startDate.atTime(LocalTime.of(0, 0, 0));
            endDateTime = endDate.atTime(0,0,10);
        } catch (Exception e) {
            throw new RuntimeException("날짜 형식 올바르게 입력해주세요~!~!");
        }

        if (startDateTime.isAfter(endDateTime)) {
            throw new RuntimeException("마감일은 시작일보다 빠를 수 없습니다~!~!");
        }

        int result = 0;
        try {
            result = mapper.updateBoardDnD(p);
        } catch (Exception e) {
            throw new RuntimeException("DnD 업데이트 쿼링 이슈~!~!");
        }

        return p.getBoardId();
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    @Transactional
    public int deleteBoard(List<DeleteBoardReq> p){

        List<List<Long>> deltagList = new ArrayList<>();
        for (DeleteBoardReq req : p) {
               deltagList.add(tagMapper.getTagByBoardId(req.getBoardId()));
        }

        if (p == null) {
            throw new RuntimeException("삭제할 보드의 리스트 넘겨주세요~!~!");
        }
        for (DeleteBoardReq deleteBoardReq : p) {
            if (deleteBoardReq.getBoardId() == null) {
                throw new RuntimeException("삭제할 보드의 id가 없습니다~!~!");
            }
        }

        for (DeleteBoardReq req : p) {
            try {
                mapper.deleteBoard(req);
            } catch (Exception e) {
                throw new RuntimeException("보드 삭제 쿼링 이슈~!~!");
            }

            try {
                customFileUtils.deleteFolder(String.format("%scalendar/%d/board/%d", customFileUtils.uploadPath, req.getCalendarId(), req.getBoardId()));
            } catch (Exception e) {
                throw new RuntimeException("로컬에서 삭제 실패!!!!!!!!!");
            }
        }

        //태그도 삭제해줘야함.
        for (List<Long> taglist : deltagList) {
            tagService.deleteTagPermanent(taglist);
        }


        return 1;
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    @Transactional
    public int deleteFile(DeleteFileReq p) {
        if (p.getBoardId() == null || p.getFileId() == null ||
                p.getFileName() == null || p.getCalendarId() == null) {
            throw new RuntimeException("모든 항목의 값을 채워야합니다.");
        }

        try {
            mapper.deleteFile(p);
        } catch (Exception e) {
            throw new RuntimeException("파일 삭제 쿼리 이슈~!~!");
        }

        try {
            File file = new File(String.format("%scalendar/%d/board/%d/%s", customFileUtils.uploadPath, p.getCalendarId(), p.getBoardId(), p.getFileName()));
            file.delete();
        } catch (Exception e) {
            throw new RuntimeException("로컬에서 삭제 실패!!!!!!!!!");
        }

        return 1;
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    public FileRes createFile(MultipartFile file, CreateFileReq p) {
        String uploadedFile = "";

        if (p.getBoardId() == null || p.getCalendarId() == null || file == null || file.isEmpty()) {
            throw new RuntimeException("모든 항목을 채우셔야 합니다.");
        }

        try {
            String path = String.format("calendar/%d/board/%d", p.getCalendarId(), p.getBoardId());
            customFileUtils.makeFolder(path);

            String newName = customFileUtils.makeRandomFileName(file);
            p.setFileName(newName);
            String target = String.format("%s/%s", path, newName);
            customFileUtils.transferTo(file, target);
            uploadedFile = target; // 경로 저장 수정

        } catch (Exception e) {
            File uploadedfile = new File(uploadedFile);
            uploadedfile.delete();
            throw new RuntimeException("업로드 안된다고~!~!", e);
        }

        try {
            mapper.createFile(p);
        } catch (Exception e) {
            File uploadedfile = new File(uploadedFile);
            uploadedfile.delete();
            throw new RuntimeException("파일이 db에 저장이 안된다고~!~!", e);
        }

        return FileRes.builder()
                .fileName(p.getFileName())
                .fileId(p.getFileId())
                .build();
    }
}
