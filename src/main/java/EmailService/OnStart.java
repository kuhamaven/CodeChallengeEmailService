package alicestudios.EmailService;

import alicestudios.EmailService.dtos.combat.CombatResultDto;
import alicestudios.EmailService.enums.DigimonNames;
import alicestudios.EmailService.enums.Stage;
import alicestudios.EmailService.models.ListingTag;
import alicestudios.EmailService.repositories.AuthorizedMailRepository;
import alicestudios.EmailService.repositories.ListingTagRepository;
import alicestudios.EmailService.services.CombatService;
import alicestudios.EmailService.services.SaveDataService;
import alicestudios.EmailService.services.UserService;
import alicestudios.EmailService.services.VersionService;
import alicestudios.EmailService.cron.DigimonUpdateCron;
import alicestudios.EmailService.dtos.user.UserRegisterDto;
import alicestudios.EmailService.models.AuthorizedMail;
import alicestudios.EmailService.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class OnStart {

    private static final boolean RUN_ON_START = true;

    private final UserService userService;
    private final SaveDataService saveDataService;
    private final CombatService combatService;
    private final DigimonUpdateCron digimonUpdateCron;
    private final AuthorizedMailRepository authorizedMailRepository;
    private final VersionService versionService;
    private final ListingTagRepository listingTagRepository;

    @Autowired
    public OnStart(UserService userService, SaveDataService saveDataService, CombatService combatService, DigimonUpdateCron digimonUpdateCron, AuthorizedMailRepository authorizedMailRepository, VersionService versionService, ListingTagRepository listingTagRepository) {
        this.userService = userService;
        this.saveDataService = saveDataService;
        this.combatService = combatService;
        this.digimonUpdateCron = digimonUpdateCron;
        this.authorizedMailRepository = authorizedMailRepository;
        this.versionService = versionService;
        this.listingTagRepository = listingTagRepository;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        if (RUN_ON_START) {
            try {
                if (!userService.usernameExists("analogman")) {
                    UserRegisterDto analogman = new UserRegisterDto("analogman@digimon.com", "analogman", "digimonworld1digimonworld2digimonworld3");
                    User analog = userService.saveUser(analogman);
                    analog.getDigimon().getRaising().setDigimonName(DigimonNames.Mugendramon);
                    saveDataService.saveUserWithElo(analog,1000);

                    UserRegisterDto taichiR = new UserRegisterDto("taichi@digimon.com", "taichi", "digimonworld1digimonworld2digimonworld3");
                    User taichi = userService.saveUser(taichiR);
                    taichi.getDigimon().getRaising().setDigimonName(DigimonNames.AeroVDramon);
                    saveDataService.saveUserWithElo(taichi,1500);

                    UserRegisterDto taigaR = new UserRegisterDto("taiga@digimon.com", "taiga", "digimonworld1digimonworld2digimonworld3");
                    User taiga = userService.saveUser(taigaR);
                    taiga.getDigimon().getRaising().setDigimonName(DigimonNames.MetalGreymon);
                    saveDataService.saveUserWithElo(taiga,1400);

                    UserRegisterDto mameoR = new UserRegisterDto("mameo@digimon.com", "mameo", "digimonworld1digimonworld2digimonworld3");
                    User mameo = userService.saveUser(mameoR);
                    mameo.getDigimon().getRaising().setDigimonName(DigimonNames.Mamemon);
                    saveDataService.saveUserWithElo(mameo,1600);

                    UserRegisterDto franR = new UserRegisterDto("fran@digimon.com", "fran", "digimonworld1digimonworld2digimonworld3");
                    User fran = userService.saveUser(franR);
                    fran.getDigimon().getRaising().setDigimonName(DigimonNames.Drimogemon);
                    saveDataService.saveUserWithElo(fran,1300);

                    UserRegisterDto numegirlR = new UserRegisterDto("numegirl@digimon.com", "numegirl", "digimonworld1digimonworld2digimonworld3");
                    User numeGirl = userService.saveUser(numegirlR);
                    numeGirl.getDigimon().getRaising().setDigimonName(DigimonNames.Numemon);
                    saveDataService.saveUserWithElo(numeGirl,1200);

                    CombatResultDto combatA = new CombatResultDto("taichi","taiga",DigimonNames.AeroVDramon,DigimonNames.MetalGreymon,false,false,"ST-000,ST-000,ST-000,ST-000,ST-000","ST-000,ST-000,ST-000,ST-000,ST-000",45,65, Stage.Perfect,Stage.Perfect);
                    CombatResultDto combatB = new CombatResultDto("mameo","taichi",DigimonNames.Mamemon,DigimonNames.AeroVDramon,true,false,"ST-000,ST-000,ST-000,ST-000,ST-000","ST-000,ST-000,ST-000,ST-000,ST-000",55,15, Stage.Perfect,Stage.Perfect);
                    CombatResultDto combatC = new CombatResultDto("numegirl","fran",DigimonNames.Numemon,DigimonNames.Drimogemon,false,true,"ST-000,ST-000,ST-000,ST-000,ST-000","ST-000,ST-000,ST-000,ST-000,ST-000",-35,19, Stage.Adult,Stage.Adult);

                    combatService.saveBattleInternally(combatA);
                    combatService.saveBattleInternally(combatB);
                    combatService.saveBattleInternally(combatC);
                }

                if (authorizedMailRepository.findByEmail("kuharokun@gmail.com").isEmpty()){

                    List<String> records = new ArrayList<>();

                    Resource resource = new ClassPathResource("emails.csv");
                    try (InputStream inputStream = resource.getInputStream()) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] values = line.split(",");
                            records.add(values[0]);
                        }
                    }

                    records.forEach(mail ->{
                        AuthorizedMail authorizedMail = new AuthorizedMail();
                        authorizedMail.setEmail(mail);
                        authorizedMailRepository.save(authorizedMail);
                    });
                }

                if (versionService.getLatestVersion().equals("")){
                    versionService.serverSideUpdate("0.7.3");
                }

                if(listingTagRepository.findByTagName("white").isEmpty()){
                     final List<String> itemTags = new ArrayList<>(List.of(new String[]{"isItem", "hasBuff", "isConsumable"}));
                     final List<String> driverTags = new ArrayList<>(List.of("isDriver", "blue", "red", "yellow", "green", "white", "cost5", "cost10",
                            "cost15", "cost20", "cost25", "cost30", "cost35", "cost40", "cost45", "cost50"));

                     itemTags.forEach(s -> {
                         ListingTag tag = new ListingTag();
                         tag.setTagName(s);
                         listingTagRepository.save(tag);
                     });

                    driverTags.forEach(s -> {
                        ListingTag tag = new ListingTag();
                        tag.setTagName(s);
                        listingTagRepository.save(tag);
                    });
                }

                digimonUpdateCron.updateDigimon();
            } catch (Exception ignored) {
                System.out.println(ignored.getMessage());
            }
        }
    }

}