package dat3.domain.dto;

import dat3.domain.entity.Hotel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HotelRequest {

    private String name;
    private String street;
    private String city;
    private String zip;
    private String country;
    private String phoneNumber;
    private String email;

    public static Hotel getHotelEntity(HotelRequest hotelRequest) {
        return new Hotel(
                hotelRequest.getName(),
                hotelRequest.getStreet(),
                hotelRequest.getCity(),
                hotelRequest.getZip(),
                hotelRequest.getCountry(),
                hotelRequest.getPhoneNumber(),
                hotelRequest.getEmail()
        );
    }

    public HotelRequest(Hotel hotel) {
        this.name = hotel.getName();
        this.street = hotel.getStreet();
        this.city = hotel.getCity();
        this.zip = hotel.getZip();
        this.country = hotel.getCountry();
        this.phoneNumber = hotel.getPhoneNumber();
        this.email = hotel.getEmail();
    }
}
