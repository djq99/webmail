package Dmail.misc;

/**
 * Created by jiaqi on 11/15/14.
 */
import Dmail.managers.ErrorManager;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

public class STListener implements STErrorListener {
    @Override
    public void compileTimeError(STMessage stMessage) {
        ErrorManager.instance().error(stMessage.toString());
    }

    @Override
    public void runTimeError(STMessage stMessage) {
        ErrorManager.instance().error(stMessage.toString());
    }

    @Override
    public void IOError(STMessage stMessage) {
        ErrorManager.instance().error(stMessage.toString());
    }

    @Override
    public void internalError(STMessage stMessage) {
        ErrorManager.instance().error(stMessage.toString());
    }
}