package sjs.instagram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import sjs.instagram.service.fileStore.FileStoreService;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class FileStoreController {
    private final FileStoreService fileStoreService;

    @ResponseBody
    @GetMapping("/fileStore/{storeFileName}")
    public Resource download(@PathVariable(name = "storeFileName") String storeFileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStoreService.getFullPath(storeFileName));
    }
}
