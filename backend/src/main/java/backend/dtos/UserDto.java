package backend.dtos;

public record UserDto  (
    Long id,
    String username,
    String email,
    String phoneNumber,
    String codeCustomer,
    String address,
    String postalCode,
    String city,
    String website
    
    ) {
      
}
