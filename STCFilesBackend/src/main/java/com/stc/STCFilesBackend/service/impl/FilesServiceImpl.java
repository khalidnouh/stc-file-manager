package com.stc.STCFilesBackend.service.impl;

import com.stc.STCFilesBackend.domain.Files;
import com.stc.STCFilesBackend.repository.FilesRepository;
import com.stc.STCFilesBackend.service.FilesService;
import com.stc.STCFilesBackend.service.dto.FilesDTO;
import com.stc.STCFilesBackend.service.mapper.FilesMapper;
import com.stc.STCFilesBackend.service.utils.FileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FilesServiceImpl implements FilesService {

    private final Logger log = LoggerFactory.getLogger(FilesServiceImpl.class);

    private final FilesRepository filesRepository;

    private final FilesMapper filesMapper;

    public FilesServiceImpl(FilesRepository filesRepository, FilesMapper filesMapper) {
        this.filesRepository = filesRepository;
        this.filesMapper = filesMapper;
    }

    @Override
    public FilesDTO save(FilesDTO filesDTO) {
        log.debug("Request to save Files : {}", filesDTO);
        Files files = filesMapper.toEntity(filesDTO);
        //
        files = filesRepository.save(files);
        return filesMapper.toDto(files);
    }

    @Override
    public Optional<FilesDTO> partialUpdate(FilesDTO filesDTO) {
        log.debug("Request to partially update files : {}", filesDTO);

        return filesRepository
                .findById(filesDTO.getId())
                .map(existingFiles -> {
                    filesMapper.partialUpdate(existingFiles, filesDTO);
                    return existingFiles;
                })
                .map(filesRepository::save)
                .map(filesMapper::toDto);
    }

    @Override
    public List<FilesDTO> findAll() {
        List<Files> files = filesRepository.findAll();
        return files.stream().map(filesMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FilesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all files");
        return filesRepository.findAll(pageable).map(filesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FilesDTO> findOne(Long id) {
        log.debug("Request to get file : {}", id);
        return filesRepository.findById(id).map(filesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete files : {}", id);
        filesRepository.deleteById(id);
    }

    @Override
    public FileOutputStream downloadFile(FilesDTO filesDTO) {
        byte[] data = filesDTO.getBinaryFile();
        File someFile = new File("D://report.pdf");
        FileResponse reportResponse = new FileResponse();
        reportResponse.setReportName("");
        reportResponse.setContent(data);
        reportResponse.setExtension("format");
        try {
            FileOutputStream fos = new FileOutputStream(someFile);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
}
