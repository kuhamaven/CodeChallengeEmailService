package alicestudios.EmailService.controllers;

import alicestudios.EmailService.services.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
public class StatusController {

    private VersionService versionService;

    @Autowired
    public StatusController(VersionService versionService)
    {
        this.versionService = versionService;
    }

//    @GetMapping("/server")
//    public HttpStatus getUserModel() {
//        return HttpStatus.OK;
//    }

    @GetMapping("/version")
    public String getCurrentVersion() {
        return versionService.getLatestVersion();
    }

    @PutMapping("/version")
    public void updateLatestVersion(@RequestBody String newVersion){
        versionService.updateLatestVersion(newVersion);
    }

}
