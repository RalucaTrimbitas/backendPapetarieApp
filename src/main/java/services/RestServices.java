package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import domain.dtos.*;
import domain.enums.StatusComanda;
import domain.validators.ResetPasswordValidator;
import domain.validators.ResetareParolaClientValidator;
import domain.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import repository.*;
import services.security.JwtUtil;
import services.security.MyUserDetailsService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin
@RestController
public class RestServices {
    private final ClientRepository clientRepository = new ClientRepository();
    private final AdministratorRepository administratorRepository = new AdministratorRepository();
    private final CategorieRepository categorieRepository = new CategorieRepository();
    private final ComandaRepository comandaRepository = new ComandaRepository();
    private final CosCumparaturiRepository cosCumparaturiRepository = new CosCumparaturiRepository();
    private final CosCumparaturi_ProdusRepository cosCumparaturi_produsRepository = new CosCumparaturi_ProdusRepository();
    private final Comanda_ProdusRepository comanda_produsRepository = new Comanda_ProdusRepository();
    private final ProdusRepository produsRepository = new ProdusRepository();
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil = new JwtUtil();
    private final MyUserDetailsService userDetailsService = new MyUserDetailsService();

    @PostMapping("/autentificare")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Utilizator utilizator) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(utilizator.getNumeUtilizator(), utilizator.getParola())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(utilizator.getNumeUtilizator());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        Client client = clientRepository.findOne(utilizator.getNumeUtilizator());
        Administrator administrator = administratorRepository.findOne(utilizator.getNumeUtilizator());
        if (client != null) {
            authenticationResponse.setUserType("client");
            authenticationResponse.setName(client.getPrenume());
        }
        if (administrator != null) {
            authenticationResponse.setUserType("administrator");
            authenticationResponse.setName(administrator.getPrenume());
        }
        authenticationResponse.setJwt(jwt);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/trimite-mail")
    public ResponseEntity<String> sendEmail(@RequestBody String recipientEmail, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        String link;
        String fullName;
        MimeMessageHelper helper = new MimeMessageHelper(message);
        Client client = clientRepository.findOneByEmail(recipientEmail);
        Administrator administrator = administratorRepository.findOneByEmail(recipientEmail);
        if (client != null) {
            final UserDetails userDetails = userDetailsService.loadUserByEmail(client.getNumeUtilizator());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            link = request.getHeader("Origin") + "/resetare-parola?token= " + jwt;
            fullName = client.getNume() + " " + client.getPrenume();
        } else if (administrator != null) {
            final UserDetails userDetails = userDetailsService.loadUserByEmail(administrator.getNumeUtilizator());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            link = request.getHeader("Origin") + "/resetare-parola?token= " + jwt;
            fullName = administrator.getNume() + " " + administrator.getPrenume();
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        helper.setFrom("papetariadiana@yahoo.com", "Papetaria Diana");
        helper.setTo(recipientEmail);

        String subject = "Resetare parola";
        String content = "<p>Salut, " + fullName + "</p>"
                + "<p>Pentru resetarea parolei, acceseaza link-ul de mai jos.</p>"
                + "<p><a href=\"" + link + "\">Schimba parola</a></p>"
                + "<br>";
//                + "<p>Daca nu ai initiat tu aceasta cerere, te rugam sa ignori acest email.";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("resetare-parola-client")
    public ResponseEntity<String> resetPasswordClient(@RequestBody ResetareParolaClient resetPassword) throws Validator.ValidationException {
        ResetareParolaClientValidator validator = new ResetareParolaClientValidator();
        try {
            validator.validate(resetPassword);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        Client client = clientRepository.findOne(resetPassword.getNumeUtilizator());
        if (client != null) {
            if (!client.getParola().equals(resetPassword.getParola_actuala()))
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            client.setParola(resetPassword.getParola_noua());
            if (clientRepository.update(client) == null)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/resetare-parola")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPassword resetPassword) throws Validator.ValidationException {
        ResetPasswordValidator validator = new ResetPasswordValidator();
        try {
            validator.validate(resetPassword);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        String username = jwtTokenUtil.extractUsername(resetPassword.getToken());
        Client client = clientRepository.findOne(username);
        if (client != null) {
            client.setParola(resetPassword.getParola());
            if (clientRepository.update(client) == null)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Administrator administrator = administratorRepository.findOne(username);
        if (administrator != null) {
            administrator.setParola(resetPassword.getParola());
            if (administratorRepository.update(administrator) == null)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/inregistrare")
    public ResponseEntity<String> registerClient(@RequestBody ClientDTO clientDTO) throws Validator.ValidationException {
        if (!clientDTO.getParola().equals(clientDTO.getConfirmParola()))
            return new ResponseEntity<>("Parolele nu corespund", HttpStatus.UNAUTHORIZED);
        if (administratorRepository.findOne(clientDTO.getNumeUtilizator()) != null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        Client clientReturned;
        Client client = new Client(clientDTO.getNumeUtilizator(), clientDTO.getParola(), clientDTO.getNume(), clientDTO.getPrenume(), clientDTO.getEmail(), clientDTO.getTip(), clientDTO.getCompanie(), clientDTO.getCodFiscal(), clientDTO.getNumarTelefon());
        try {
            clientReturned = clientRepository.save(client);
        } catch (IllegalArgumentException | Validator.ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (clientReturned == null) {
            CosCumparaturi cosCumparaturi = new CosCumparaturi();
            cosCumparaturi.setNumeUtilizatorClient(client.getNumeUtilizator());
            cosCumparaturiRepository.save(cosCumparaturi);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    //ClientServices
    @GetMapping("/client")
    public ResponseEntity<List<Client>> getClienti() {
        List<Client> list = clientRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/client")
    public ResponseEntity<String> saveClient(@RequestBody Client client) {
        Client clientReturned;
        try {
            clientReturned = clientRepository.save(client);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (clientReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/client/{numeUtilizatorClient}")
    public ResponseEntity<String> deleteClient(@PathVariable String numeUtilizatorClient) {
        Client client = clientRepository.delete(numeUtilizatorClient);
        if (client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/client")
    public ResponseEntity<String> updateClient(@RequestBody Client client) {
        Client clientReturned;
        try {
            clientReturned = clientRepository.update(client);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (clientReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/client/{numeUtilizatorClient}")
    public ResponseEntity<Client> findOneClient(@PathVariable String numeUtilizatorClient) {
        Client client = clientRepository.findOne(numeUtilizatorClient);
        if (client != null)
            return new ResponseEntity<>(client, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //AdministratorServices
    @PostMapping("/administrator")
    public ResponseEntity<String> saveAdministrator(@RequestBody Administrator administrator) {
        Administrator administrator1;
        try {
            administrator1 = administratorRepository.save(administrator);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (administrator1 == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/administrator/{numeUtilizatorAdministrator}")
    public ResponseEntity<String> deleteAdministrator(@PathVariable String numeUtilizatorAdministrator) {
        Administrator administrator = administratorRepository.delete(numeUtilizatorAdministrator);
        if (administrator == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/administrator")
    public ResponseEntity<List<Administrator>> getAdministrators() {
        List<Administrator> list = administratorRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    //ProdusServices
    @GetMapping("/produse")
    public ResponseEntity<List<ProdusDTO>> getProduse() {
        List<Produs> list = produsRepository.findAll();
        List<ProdusDTO> dtoList = new ArrayList<>();
        if (!list.isEmpty()) {
            list.forEach(produs -> {
                ProdusDTO produsDTO = new ProdusDTO();
                produsDTO.setCodDeBare(produs.getCodDeBare());
                produsDTO.setDenumire(produs.getDenumire());
                produsDTO.setPret(produs.getPret());
                produsDTO.setDescriere(produs.getDescriere());
                produsDTO.setSrc(produs.getSrc());
                produsDTO.setIdCategorieProdus(produs.getIdCategorieProdus());
                produsDTO.setDetalii(produs.getDetalii());
                dtoList.add(produsDTO);
            });
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/produse")
    public ResponseEntity<String> saveProdus(@RequestParam(value = "image", required = false) MultipartFile image, @RequestParam("produs") String jsonProdus) throws JsonProcessingException {
        Produs produsReturned;
        Produs produs = objectMapper.readValue(jsonProdus, Produs.class);
        if (image == null) {
            return new ResponseEntity<>("Adaugarea unei imagini cu produsul este obligatorie!", HttpStatus.CONFLICT);
        }
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        if (fileName.contains("..")) {
            return new ResponseEntity<>("Fisier invalid", HttpStatus.EXPECTATION_FAILED);
        }
        try {
            produs.setSrc(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (administratorRepository.findOne(produs.getNumeUtilizatorAdministrator()) == null)
            return new ResponseEntity<>("Administratorul cu numele de utilizator: "
                    + produs.getNumeUtilizatorAdministrator() + " nu exista.", HttpStatus.CONFLICT);
        if (categorieRepository.findOne(produs.getIdCategorieProdus()) == null)
            return new ResponseEntity<>("Categoria introdusa nu exista.", HttpStatus.CONFLICT);
        if (produsRepository.findOne(produs.getCodDeBare()) != null)
            return new ResponseEntity<>("Exista deja un produs cu acest cod de bare!", HttpStatus.CONFLICT);
        try {
            produsReturned = produsRepository.save(produs);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (produsReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/produse/{codDeBare}")
    public ResponseEntity<String> deleteProdus(@PathVariable String codDeBare) {
        Produs produs = produsRepository.delete(codDeBare);
        if (produs == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/produse")
    public ResponseEntity<String> updateProdus(@RequestParam(value = "image", required = false) MultipartFile image, @RequestParam("produs") String jsonProdus) throws JsonProcessingException {
        Produs produsReturned;
        Produs produs = objectMapper.readValue(jsonProdus, Produs.class);
        if (image != null) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            if (fileName.contains("..")) {
                return new ResponseEntity<>("Fisier invalid", HttpStatus.EXPECTATION_FAILED);
            }
            try {
                produs.setSrc(Base64.getEncoder().encodeToString(image.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (administratorRepository.findOne(produs.getNumeUtilizatorAdministrator()) == null)
            return new ResponseEntity<>("Administratorul cu numele de utilizator: "
                    + produs.getNumeUtilizatorAdministrator() + " nu exista.", HttpStatus.CONFLICT);
        if (categorieRepository.findOne(produs.getIdCategorieProdus()) == null)
            return new ResponseEntity<>("Categoria introdusa nu exista.", HttpStatus.CONFLICT);
        try {
            produsReturned = produsRepository.update(produs);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (produsReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/produse/{codDeBare}")
    public ResponseEntity<Produs> findOneProdus(@PathVariable String codDeBare) {
        Produs produs = produsRepository.findOne(codDeBare);
        if (produs != null)
            return new ResponseEntity<>(produs, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //CategorieServices
    @GetMapping("/categorii")
    public ResponseEntity<List<Categorie>> getCategorii() {
        List<Categorie> list = categorieRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/categorii")
    public ResponseEntity<String> saveCategorie(@RequestBody Categorie categorie) {
        Categorie categorieReturned;
        try {
            categorieReturned = categorieRepository.save(categorie);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (categorieReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/categorii/{idCategorie}")
    public ResponseEntity<String> deleteCategorie(@PathVariable String idCategorie) {
        Categorie categorie = categorieRepository.delete(idCategorie);
        if (categorie == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/categorii")
    public ResponseEntity<String> updateCategorie(@RequestBody Categorie categorie) {
        Categorie categorieReturned;
        try {
            categorieReturned = categorieRepository.update(categorie);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (categorieReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/categorii/{idCategorie}")
    public ResponseEntity<Categorie> findOneCategorie(@PathVariable String idCategorie) {
        Categorie categorie = categorieRepository.findOne(idCategorie);
        if (categorie != null)
            return new ResponseEntity<>(categorie, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //ComandaServices
    @GetMapping("/comenzi-plasate")
    public ResponseEntity<List<Comanda>> getComenziPlasate() {
        List<Comanda> listToReturned = comandaRepository.findAll();
        listToReturned.sort(Comparator.comparing(Comanda::getDataPlasare).reversed());
        if (listToReturned.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(listToReturned, HttpStatus.OK);
    }


    @GetMapping("/comenzi/{numeUtilizator}")
    public ResponseEntity<List<Comanda>> getComenzi(@PathVariable String numeUtilizator) {
        List<Comanda> listToReturned = new ArrayList<>();
        List<Comanda> list = comandaRepository.findAll();
        for (Comanda comanda : list) {
            if (comanda.getNumeUtilizatorClient().equals(numeUtilizator)) {
                listToReturned.add(comanda);
            }
        }
        listToReturned.sort(Comparator.comparing(Comanda::getDataPlasare).reversed());
        if (listToReturned.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(listToReturned, HttpStatus.OK);
    }

    @PostMapping("/comenzi/{numeUtilizator}")
    public ResponseEntity<String> saveComanda(@PathVariable String numeUtilizator, @RequestBody Map<String, List<ProdusCosDTO>> cart) {
        List<ProdusCosDTO> produsCosDTOList = cart.get("cart");

        Comanda addComanda = new Comanda();
        addComanda.setNumeUtilizatorClient(numeUtilizator);
        addComanda.setDataPlasare(LocalDateTime.now());
        float suma = 0;
        for (ProdusCosDTO produsCosDTO : produsCosDTOList) {
            suma += produsCosDTO.getCantitate() * produsCosDTO.getPret();
        }
        addComanda.setSuma(suma);
        addComanda.setTva(19 * suma / 100);
        addComanda.setTotal(suma);
        addComanda.setStatus(StatusComanda.IN_PREGATIRE);
        Comanda comandaReturned;
        if (clientRepository.findOne(addComanda.getNumeUtilizatorClient()) == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        try {
            comandaReturned = comandaRepository.save(addComanda);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (comandaReturned != null) {
            produsCosDTOList.forEach(produs -> {
                Comanda_Produs comandaProdus = new Comanda_Produs();
                comandaProdus.setIdProdus(produs.getCodDeBare());
                comandaProdus.setIdComanda(comandaReturned.getNumarComanda());
                comandaProdus.setId_Comanda_Produs(comandaProdus.getIdComanda() + "_" + comandaProdus.getIdProdus());
                comandaProdus.setCantitate(produs.getCantitate());
                try {
                    comanda_produsRepository.save(comandaProdus);
                    clearCos(produs.getIdCosCumparaturiProdus().split("_")[0]);
                } catch (Validator.ValidationException e) {
                    e.printStackTrace();
                }
            });
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    public void clearCos(String idCosCumparaturi) {
        cosCumparaturi_produsRepository.findAll().forEach(element -> {
                    if (element.getId_CosCumparaturi_Produs().startsWith(idCosCumparaturi))
                        cosCumparaturi_produsRepository.delete(element.getId_CosCumparaturi_Produs());
                }
        );
    }

    @DeleteMapping("/comenzi/{numarComanda}")
    public ResponseEntity<String> deleteComanda(@PathVariable String numarComanda) {
        Comanda comanda = comandaRepository.delete(numarComanda);
        if (comanda == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/comenzi")
    public ResponseEntity<String> updateComanda(@RequestBody Comanda comanda) {
        Comanda comandaReturned;
        StatusComanda statusComanda = comanda.getStatus();
        comanda = comandaRepository.findOne(String.valueOf(comanda.getNumarComanda()));
        comanda.setStatus(statusComanda);
        try {
            comandaReturned = comandaRepository.update(comanda);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (comandaReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

//    @GetMapping("/comenzi/{numarComanda}")
//    public ResponseEntity<Comanda> findOneComanda(@PathVariable String numarComanda) {
//        Comanda comanda = comandaRepository.findOne(numarComanda);
//        if (comanda != null)
//            return new ResponseEntity<>(comanda, HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    //CosCumparaturiServices
    @GetMapping("/cos-cumparaturi")
    public ResponseEntity<List<CosCumparaturi>> getCosCumparaturi() {
        List<CosCumparaturi> list = cosCumparaturiRepository.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/cos-cumparaturi")
    public ResponseEntity<String> saveCosCumparaturi(@RequestBody CosCumparaturi cosCumparaturi) {
        CosCumparaturi cosCumparaturiReturned;
        if (clientRepository.findOne(cosCumparaturi.getNumeUtilizatorClient()) == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        try {
            cosCumparaturiReturned = cosCumparaturiRepository.save(cosCumparaturi);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (cosCumparaturiReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/cos-cumparaturi/{idCosCumparaturi}")
    public ResponseEntity<String> deleteCosCumparaturi(@PathVariable String idCosCumparaturi) {
        CosCumparaturi cosCumparaturi = cosCumparaturiRepository.delete(idCosCumparaturi);
        if (cosCumparaturi == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/cos-cumparaturi")
    public ResponseEntity<String> updateComanda(@RequestBody CosCumparaturi cosCumparaturi) {
        CosCumparaturi cosCumparaturiReturned;
        try {
            cosCumparaturiReturned = cosCumparaturiRepository.update(cosCumparaturi);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (cosCumparaturiReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/cos-cumparaturi/{idCosCumparaturi}")
    public ResponseEntity<CosCumparaturi> findOneCosCumparaturi(@PathVariable String idCosCumparaturi) {
        CosCumparaturi cosCumparaturi = cosCumparaturiRepository.findOne(idCosCumparaturi);
        if (cosCumparaturi != null)
            return new ResponseEntity<>(cosCumparaturi, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/cos-cumparaturi/personal/{numeUtilizator}")
    public ResponseEntity<CosCumparaturi> findOneCosCumparaturiUtilizator(@PathVariable String numeUtilizator) {
        CosCumparaturi cosCumparaturi = cosCumparaturiRepository.findOneUtilizator(numeUtilizator);
        if (cosCumparaturi != null)
            return new ResponseEntity<>(cosCumparaturi, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //CosCumparaturi_ProdusServices
    @GetMapping("/cos-cumparaturi-produs/{numeUtilizator}")
    public ResponseEntity<List<ProdusCosDTO>> getCosCumparaturiProdus(@PathVariable String numeUtilizator) {
        CosCumparaturi cosCumparaturi = cosCumparaturiRepository.findOneUtilizator(numeUtilizator);
        List<ProdusCosDTO> listToReturned = new ArrayList<>();
        for (CosCumparaturi_Produs element : cosCumparaturi_produsRepository.findAll()) {
            if (element.getIdCosCumparaturi() == cosCumparaturi.getIdCosCumparaturi()) {
                Produs produs = produsRepository.findOne(element.getIdProdus());
                ProdusCosDTO produsCosDTO = new ProdusCosDTO();
                produsCosDTO.setCodDeBare(produs.getCodDeBare());
                produsCosDTO.setDenumire(produs.getDenumire());
                produsCosDTO.setPret(produs.getPret());
                produsCosDTO.setDescriere(produs.getDescriere());
                produsCosDTO.setSrc(produs.getSrc());
                produsCosDTO.setDetalii(produs.getDetalii());
                produsCosDTO.setCantitate(element.getCantitate());
                produsCosDTO.setIdCosCumparaturiProdus(element.getId_CosCumparaturi_Produs());
                listToReturned.add(produsCosDTO);
            }
        }
//        if (listToReturned.isEmpty())
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(listToReturned, HttpStatus.OK);
    }

    @PostMapping("/cos-cumparaturi-produs/{numeUtilizator}")
    public ResponseEntity<String> saveCosCumparaturiProdus(@RequestBody CosCumparaturi_Produs cosCumparaturi_Produs, @PathVariable String numeUtilizator) {
        CosCumparaturi cosCumparaturi = cosCumparaturiRepository.findOneUtilizator(numeUtilizator);
        cosCumparaturi_Produs.setIdCosCumparaturi(cosCumparaturi.getIdCosCumparaturi());
        String id_CosCumparaturi_Produs = cosCumparaturi_Produs.getIdCosCumparaturi() + "_" + cosCumparaturi_Produs.getIdProdus();
        cosCumparaturi_Produs.setId_CosCumparaturi_Produs(id_CosCumparaturi_Produs);
        cosCumparaturi_Produs.setCantitate(1);
        CosCumparaturi_Produs cosCumparaturiProdusReturned;
        try {
            cosCumparaturiProdusReturned = cosCumparaturi_produsRepository.save(cosCumparaturi_Produs);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (cosCumparaturiProdusReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        else if (cosCumparaturiProdusReturned.getId_CosCumparaturi_Produs().equals(cosCumparaturi_Produs.getId_CosCumparaturi_Produs())) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/cos-cumparaturi-produs")
    public ResponseEntity<String> updateCosCumparaturiProdus(@RequestBody CosCumparaturi_Produs cosCumparaturi_produs) {
        CosCumparaturi_Produs cosCumparaturiProdusReturned;
        try {
            cosCumparaturiProdusReturned = cosCumparaturi_produsRepository.update(cosCumparaturi_produs);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (cosCumparaturiProdusReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/cos-cumparaturi-produs/{id_CosCumparaturi_Produs}")
    public ResponseEntity<String> deleteCosCumparaturiProdus(@PathVariable String id_CosCumparaturi_Produs) {
        CosCumparaturi_Produs cosCumparaturiProdus = cosCumparaturi_produsRepository.delete(id_CosCumparaturi_Produs);
        if (cosCumparaturiProdus == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Comanda_ProdusServices
    @GetMapping("/comanda-produs/{numeUtilizator}")
    public ResponseEntity<List<ProdusCosDTO>> getComandaProdus(@PathVariable String numeUtilizator) {
        Comanda comanda = comandaRepository.findOneUtilizator(numeUtilizator);
        List<ProdusCosDTO> listToReturned = new ArrayList<>();
        for (Comanda_Produs element : comanda_produsRepository.findAll()) {
            if (element.getIdComanda() == comanda.getNumarComanda()) {
                Produs produs = produsRepository.findOne(element.getIdProdus());
                ProdusCosDTO produsCosDTO = new ProdusCosDTO();
                produsCosDTO.setCodDeBare(produs.getCodDeBare());
                produsCosDTO.setDenumire(produs.getDenumire());
                produsCosDTO.setPret(produs.getPret());
                produsCosDTO.setDescriere(produs.getDescriere());
                produsCosDTO.setSrc(produs.getSrc());
                produsCosDTO.setDetalii(produs.getDetalii());
                listToReturned.add(produsCosDTO);
            }
        }
        if (listToReturned.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(listToReturned, HttpStatus.OK);
    }

    @GetMapping("/comanda-istoric/{numarComanda}")
    public ResponseEntity<List<ProdusCosDTO>> getComandaIstoric(@PathVariable String numarComanda) {
        Comanda comanda = comandaRepository.findOne(numarComanda);
        List<ProdusCosDTO> listToReturned = new ArrayList<>();
        for (Comanda_Produs element : comanda_produsRepository.findAll()) {
            if (element.getIdComanda() == comanda.getNumarComanda()) {
                Produs produs = produsRepository.findOne(element.getIdProdus());
                ProdusCosDTO produsCosDTO = new ProdusCosDTO();
                produsCosDTO.setCodDeBare(produs.getCodDeBare());
                produsCosDTO.setDenumire(produs.getDenumire());
                produsCosDTO.setPret(produs.getPret());
                produsCosDTO.setDescriere(produs.getDescriere());
                produsCosDTO.setSrc(produs.getSrc());
                produsCosDTO.setDetalii(produs.getDetalii());
                produsCosDTO.setCantitate(element.getCantitate());
                listToReturned.add(produsCosDTO);
            }
        }
        if (listToReturned.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(listToReturned, HttpStatus.OK);
    }

    @PostMapping("/comanda-produs/{numeUtilizator}")
    public ResponseEntity<String> saveComandaProdus(@RequestBody Comanda_Produs comanda_Produs, @PathVariable String numeUtilizator) {
        Comanda comanda = comandaRepository.findOneUtilizator(numeUtilizator);
        comanda_Produs.setIdComanda(comanda.getNumarComanda());
        String id_Comanda_Produs = comanda_Produs.getIdComanda() + "_" + comanda_Produs.getIdProdus();
        comanda_Produs.setId_Comanda_Produs(id_Comanda_Produs);
        Comanda_Produs comandaProdusReturned;
        try {
            comandaProdusReturned = comanda_produsRepository.save(comanda_Produs);
        } catch (Validator.ValidationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        if (comandaProdusReturned == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/comanda-produs/{id_Comanda_Produs}")
    public ResponseEntity<String> deleteComandaProdus(@PathVariable String id_Comanda_Produs) {
        Comanda_Produs comandaProdus = comanda_produsRepository.delete(id_Comanda_Produs);
        if (comandaProdus == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
