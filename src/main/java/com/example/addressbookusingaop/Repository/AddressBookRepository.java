package com.example.addressbookusingaop.Repository;
import com.example.addressbookusingaop.Model.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook,Integer> {
    List<AddressBook> getPersonByName(String name);
    List<AddressBook> getPersonDataByCity(String city);
    List<AddressBook> getPersonDataByState(String state);
}