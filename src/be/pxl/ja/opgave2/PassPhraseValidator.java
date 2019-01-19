package be.pxl.ja.opgave2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PassPhraseValidator<T> extends Thread {
    private List<T> listToValidate;
    private boolean valid;

    public PassPhraseValidator(List<T> list) {
        listToValidate = list;
        valid = false;
    }

    @Override
    public void run() {
        Set<T> passPhraseSet = new HashSet<>();
        for (T element : listToValidate) {
            passPhraseSet.add(element);
        }

        if(passPhraseSet.size() == listToValidate.size()){
            valid = true;
        }
    }

    public String getPassPhrase() {
        StringBuilder builder = new StringBuilder();
        String passPhrase = null;

        for (T element : listToValidate) {
            builder.append(", " + element);
        }

        passPhrase = "[" + builder.toString().substring(2) + "]";
        return passPhrase;
    }

    public boolean isValid() {
        return valid;
    }
}
