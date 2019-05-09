package com.tiagofarinha.inmezzoapp.Utils;

import android.support.v4.app.Fragment;

import com.tiagofarinha.inmezzoapp.AdminTools.AdminLogic;
import com.tiagofarinha.inmezzoapp.Fragments.AboutLogic;
import com.tiagofarinha.inmezzoapp.Fragments.CandidaturasLogic;
import com.tiagofarinha.inmezzoapp.Fragments.ContactsLogic;
import com.tiagofarinha.inmezzoapp.Fragments.EnsaioLogic;
import com.tiagofarinha.inmezzoapp.Fragments.FeedLogic;
import com.tiagofarinha.inmezzoapp.Fragments.LoginLogic;
import com.tiagofarinha.inmezzoapp.Fragments.MembersLogic;
import com.tiagofarinha.inmezzoapp.Fragments.PortfolioLogic;
import com.tiagofarinha.inmezzoapp.Fragments.ProfileLogic;
import com.tiagofarinha.inmezzoapp.Fragments.ReservesLogic;
import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;
import com.tiagofarinha.inmezzoapp.R;

public class MenuUtils {

    public static void filterMenuItem(int id) {

        MainActivity m = MainActivity.getInstance();

        Fragment frag = null;

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
                m.handleLog(MainActivity.MODE_LOGOUT);
                return;
            case R.id.menu_perfil:
                frag = new ProfileLogic();
                break;
            case R.id.menu_ensaios:
                frag = new EnsaioLogic();
                break;
            case R.id.menu_admin:
                frag = new AdminLogic();
                break;
            default:
                break;
        }

        m.changeFrag(frag, id);

    }


}