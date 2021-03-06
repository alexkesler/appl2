package org.kesler.appl2.logic.reception.filter;

import java.util.List;

import org.kesler.appl2.logic.Applicator;
import org.kesler.appl2.logic.Reception;
import org.kesler.appl2.logic.FL;
import org.kesler.appl2.logic.applicator.ApplicatorFL;

public class FLReceptionsFilter implements ReceptionsFilter {

    private FL fl;


    public FLReceptionsFilter(FL fl) {
        this.fl = fl;
    }

    public FL getFl() {
        return fl;
    }

    @Override
    public boolean checkReception(Reception reception) {
        if (reception == null) {
            throw new IllegalArgumentException();
        }

        boolean fit = false;

        List<Applicator> applicators = reception.getApplicators();

        for(Applicator applicator: applicators) {
           if(applicator.getClass().equals(ApplicatorFL.class)) {
               FL currentFL = ((ApplicatorFL)applicator).getFL();
               FL represFL = applicator.getRepres();
               if (fl.equals(currentFL) || fl.equals(represFL)) {
                   fit = true;
                   break;
               }
           }
        }

        return fit;
    }

    @Override
    public String toString() {

        String filterString  = "По физ. лицу: (" + fl.getFIO() + ")";

        return filterString;
    }

}