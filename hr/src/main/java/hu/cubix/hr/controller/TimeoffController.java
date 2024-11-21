package hu.cubix.hr.controller;

import hu.cubix.hr.dto.TimeoffDto;
import hu.cubix.hr.mapper.TimeoffMapper;
import hu.cubix.hr.model.Timeoff;
import hu.cubix.hr.repository.TimeoffRepository;
import hu.cubix.hr.security.EmployeeDetails;
import hu.cubix.hr.service.TimeoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/timeoff")
public class TimeoffController {

    @Autowired
    TimeoffService timeoffService;

    @Autowired
    TimeoffMapper timeoffMapper;
    @Autowired
    private TimeoffRepository timeoffRepository;

    @GetMapping
    public List<TimeoffDto> getAllTimeoffs() {
        List<Timeoff> timeoffs = timeoffService.findAll();
        List<TimeoffDto> timeoffDtos = new ArrayList<>();
        for (Timeoff timeoff : timeoffs) {
            timeoffDtos.add(timeoffMapper.timeoffToDto(timeoff));
        }
        return timeoffDtos;
    }

    @GetMapping("/{id}")
    public TimeoffDto getTimeoff(@PathVariable long id) {
        Timeoff timeoff = timeoffService.findById(id);
        if (timeoff == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Timeoff not found");
        }
        return timeoffMapper.timeoffToDto(timeoff);
    }

    @GetMapping("/pagesByExample")
    public Page<TimeoffDto> findAllWithPagesAndExample(Pageable page, @RequestBody(required =
            false) Timeoff timeoff, @RequestParam(required = false) LocalDate timeoffStartDate,
                                                       @RequestParam(required = false) LocalDate timeoffEndDate, @RequestParam(required = false) LocalDate creationStartDate, @RequestParam(required = false) LocalDate creationEndDate) {
        if (timeoff == null) {
            timeoff = new Timeoff();
        }
        Page<Timeoff> timeoffs = timeoffService.findTimeoffsByExample(timeoff, timeoffStartDate,
                timeoffEndDate, creationStartDate, creationEndDate, page);
        return timeoffMapper.timeoffsToDtosPage(timeoffs);
    }

    @PostMapping("/{employeeId}")
    public TimeoffDto createTimeoff(@RequestBody final TimeoffDto timeoffDto,
                                    @PathVariable long employeeId) {
        Timeoff timeoff = timeoffMapper.dtoToTimeoff(timeoffDto);
        Timeoff createdTimeoff = timeoffService.create(timeoff, employeeId);
        if (createdTimeoff == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return timeoffMapper.timeoffToDto(createdTimeoff);
    }

    @PutMapping("/{id}")
    public TimeoffDto updateTimeoff(@PathVariable long id,
                                    @RequestBody final TimeoffDto timeoffDto) {
        timeoffDto.setId(id);
        // Timeoff.AcceptStatus acceptStatus = getTimeoff(id).getAccepted();
        Timeoff timeoff = timeoffMapper.dtoToTimeoff(timeoffDto);
        // timeoff.setAccepted(acceptStatus);
        Timeoff updatedTimeoff = timeoffService.update(timeoff);
        if (updatedTimeoff == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return timeoffMapper.timeoffToDto(updatedTimeoff);

    }

    @PutMapping("/decide")
    public TimeoffDto approveTimeoff(@RequestParam long timeoffId, @RequestParam boolean decision) {
        Timeoff timeoff = timeoffService.decideTimeoff(decision, timeoffId);
        if (timeoff == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return timeoffMapper.timeoffToDto(timeoff);
    }

    @DeleteMapping("/{id}")
    public void deleteTimeoff(@PathVariable long id) {
        timeoffService.delete(id);
    }


}
