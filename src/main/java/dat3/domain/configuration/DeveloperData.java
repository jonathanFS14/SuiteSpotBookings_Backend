package dat3.domain.configuration;

import dat3.domain.entity.Hotel;
import dat3.domain.entity.Reservation;
import dat3.domain.entity.Room;
import dat3.domain.entity.User;
import dat3.domain.repository.HotelRepository;
import dat3.domain.repository.ReservationRepository;
import dat3.domain.repository.RoomRepository;
import dat3.domain.repository.UserRepository;
import dat3.domain.service.UserService;
import dat3.security.entity.Role;
import dat3.security.entity.UserWithRoles;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import dat3.security.repository.UserWithRolesRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DeveloperData implements ApplicationRunner {

    PasswordEncoder passwordEncoder;
    HotelRepository hotelRepository;
    RoomRepository roomRepository;
    ReservationRepository reservationRepository;
    UserRepository userRepository;

    public DeveloperData(PasswordEncoder passwordEncoder, HotelRepository hotelRepository, RoomRepository roomRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        setupUsers();
        setupHotels();
        setupRooms();
        setupReservations();
        }


    public void setupUsers() {
        User admin = new User("admin", "1234", "adminMail", "admin", "admin", "street", "city", "zip", "12345678");
        admin.addRole(Role.USER);
        admin.addRole(Role.ADMIN);
        userRepository.save(admin);

        User jonathan = new User("Jonathan", "1234", "jonathan@mail.kea", "jonathan", "smith", "street", "city", "zip", "12345678");
        jonathan.addRole(Role.USER);
        userRepository.save(jonathan);
    }

    public void setupHotels() {
        int numberOfHotels = 250;
        List<Hotel> hotels = new ArrayList<>();
        for (int i = 1; i <= numberOfHotels; i++) {
            String name = "Hotel " + i;
            String street = i + " Example Street";
            String city = "City " + i;
            String zip = "Zip" + (1000 + i);
            String country = "Country " + i;
            String phoneNumber = "+1234567890" + i;
            String email = "contact@hotel" + i + ".com";
            Hotel hotel = new Hotel(name, street, city, zip, country, phoneNumber, email);
            hotels.add(hotel);
        }
        hotelRepository.saveAll(hotels);
    }


    public void setupRooms() {
        List<Hotel> hotels = hotelRepository.findAll();
        int numberOfRoomsPerHotel = 5;
        for (Hotel hotel : hotels) {
            for (int i = 1; i <= numberOfRoomsPerHotel; i++) {
                String floor = "1st";
                Random random = new Random();
                String numberOfBeds = String.valueOf(random.nextInt(4) + 1);
                String roomType = "Standard";
                String roomPrice = "100";
                String roomDescription = "A standard room with all amenities";
                Room room = new Room(i, floor, numberOfBeds, roomType, roomPrice, roomDescription, hotel);
                roomRepository.save(room);
            }
        }
    }
    //test

    public void setupReservations() {
        UserService userService = new UserService(userRepository);
        User user = userService.getUserById("Jonathan");
        List<Room> rooms = roomRepository.findAll();

        for (int i = 0; i < 5; i++) {
            Room room = rooms.get(i);
            LocalDate start = LocalDate.now().plusDays(i * 2);
            LocalDate end = start.plusDays(2);
            Reservation reservation = new Reservation(user, room, start, end);
            reservationRepository.save(reservation);
        }

    }

}
