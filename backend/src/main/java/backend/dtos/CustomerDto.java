package backend.dtos;

public record CustomerDto(Long id,

    String name,

    String email,

    String phoneNumber,

    String code,

    String address,

    String postalCode,

    String city


) {
    
}
