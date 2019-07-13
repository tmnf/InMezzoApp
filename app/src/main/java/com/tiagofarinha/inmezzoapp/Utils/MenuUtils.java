package com.tiagofarinha.inmezzoapp.Utils;

import android.support.v4.app.Fragment;

import com.tiagofarinha.inmezzoapp.AdminTools.AdminLogic;
import com.tiagofarinha.inmezzoapp.Cache.FragManager;
import com.tiagofarinha.inmezzoapp.Fragments.AboutLogic;
import com.tiagofarinha.inmezzoapp.Fragments.CandidaturasLogic;
import com.tiagofarinha.inmezzoapp.Fragments.ConcertsLogic;
import com.tiagofarinha.inmezzoapp.Fragments.ContactsLogic;
import com.tiagofarinha.inmezzoapp.Fragments.EnsaiosLogic;
import com.tiagofarinha.inmezzoapp.Fragments.FeedLogic;
import com.tiagofarinha.inmezzoapp.Fragments.LoginLogic;
import com.tiagofarinha.inmezzoapp.Fragments.MembersLogic;
import com.tiagofarinha.inmezzoapp.Fragments.PortfolioLogic;
import com.tiagofarinha.inmezzoapp.Fragments.ProfileLogic;
import com.tiagofarinha.inmezzoapp.Fragments.ReservesLogic;
import com.tiagofarinha.inmezzoapp.Fragments.WarningLogic;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.R;

public class MenuUtils {

    public static void filterMenuItem(int id) {
        Fragment frag = FragManager.getInstance().findFragment("Frag:" + id);

        if (frag == null) {
            switch (id) {
                case R.id.menu_inicio:
                    frag = new FeedLogic();
                    break;
                case R.id.menu_portfolio:
                    frag = new PortfolioLogic();
                    break;
                case R.id.menu_contactos:
                    frag = new ContactsLogic();
                    break;
                case R.id.menu_sobre:
                    frag = new AboutLogic();
                    break;
                case R.id.menu_concertos:
                    frag = new ConcertsLogic();
                    break;
                case R.id.menu_membros:
                    frag = new MembersLogic();
                    break;
                case R.id.menu_candidaturas:
                    frag = new CandidaturasLogic();
                    break;
                case R.id.menu_reservas:
                    frag = new ReservesLogic();
                    break;
                case R.id.menu_login:
                    frag = new LoginLogic();
                    break;
                case R.id.menu_logout:
                    MainMethods.getInstance().handleLog(MainMethods.MODE_LOGOUT);
                    return;
                case R.id.menu_perfil:
                    frag = new ProfileLogic();
                    break;
                case R.id.menu_ensaios:
                    frag = new EnsaiosLogic();
                    break;
                case R.id.menu_admin:
                    frag = new AdminLogic();
                    break;
                case R.id.menu_warnings:
                    frag = new WarningLogic();
                    break;
                default:
                    break;
            }
            MainMethods.getInstance().changeFrag(frag, id, false);
        } else
            MainMethods.getInstance().changeFrag(frag, id, true);
    }


}
