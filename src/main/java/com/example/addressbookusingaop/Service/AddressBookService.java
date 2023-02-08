package com.example.addressbookusingaop.Service;
import com.example.addressbookusingaop.DTO.AddressBookDTO;
import com.example.addressbookusingaop.Exception.AddressBookException;
import com.example.addressbookusingaop.Model.AddressBook;
import com.example.addressbookusingaop.Repository.AddressBookRepository;
import com.example.addressbookusingaop.Util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class AddressBookService implements IAddressBookService {
    @Autowired
    private AddressBookRepository addressBookRepository;
    @Autowired
    TokenUtil tokenUtil;

    private List<AddressBook> addressBookList = new ArrayList<>();

    @Override
    public List<AddressBook> getAllPersonRecords() {

        return addressBookRepository.findAll();
    }

    @Override
    public AddressBook createPersonContact(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = null;
        addressBook = new AddressBook(addressBookDTO);
        addressBookRepository.save(addressBook);
        return addressBook;
    }

    @Override
    public AddressBook getPersonRecordById(int id) {
        return addressBookRepository.findById(id)
                .orElseThrow(() -> new AddressBookException("Person with id" + id + "doesnt exist!!"));

    }

    @Override
    public AddressBook updatePersonRecordById(int id, AddressBookDTO addressBookDTO) {
        AddressBook addressBookData = this.getPersonRecordById(id);
        addressBookData.updatePersonData(addressBookDTO);
        return addressBookRepository.save(addressBookData);

    }
    @Override
    public void deletePersonRecordById(int id) {
        Optional<AddressBook> addressBook=addressBookRepository.findById(id);
        if(addressBook.isPresent()){
            addressBookRepository.deleteById(id);
        }else {
            throw new AddressBookException("Sorry! Cannot find user id: " + id);
        }
    }
    @Override
    public List<AddressBook> getPersonRecordByName(String name) {
        List<AddressBook> getByName= addressBookRepository.getPersonByName(name);
        if(getByName.isEmpty()){
            throw new ArithmeticException("Sorry! Can not find user name: "+name);
        }else {
            return addressBookRepository.getPersonByName(name);
        }
    }

    @Override
    public List<AddressBook> getPersonRecordByCity(String city) {
        return addressBookRepository.getPersonDataByCity(city);
    }
    @Override
    public List<AddressBook> getPersonRecordByState(String state) {
        return addressBookRepository.getPersonDataByState(state);
    }

    @Override
    public AddressBook createRecordAndToken(AddressBookDTO addressBookDTO) {
        AddressBook addressbookData = null;
        addressbookData = new AddressBook(addressBookDTO);
        addressBookRepository.save(addressbookData);
        String token = tokenUtil.createToken(addressbookData.getId());
        return addressbookData;

    }
    @Override
    public AddressBook getRecordByToken(String token) {
        int id = tokenUtil.decodeToken(token);
        AddressBook addressBookData = addressBookRepository.findById(id).get();
        return addressBookData ;
    }

    @Override
    public AddressBook updateRecordByToken(String token, AddressBookDTO addressBookDTO) {
        int id = tokenUtil.decodeToken(token);
        AddressBook addressBook = this.getPersonRecordById(id);
        addressBook.updatePersonData(addressBookDTO);
        addressBookRepository.save(addressBook);
        return addressBook;

    }
    @Override
    public AddressBook deletePersonRecordByToken(String token) {
        int id = tokenUtil.decodeToken(token);
        if (addressBookRepository.findById(id).isPresent()) {
            AddressBook addressBookModel = addressBookRepository.findById(id).get();
            addressBookRepository.deleteById(id);
            return addressBookModel;
        } else {
            throw new AddressBookException("Invalid token ");
        }

    }

}
