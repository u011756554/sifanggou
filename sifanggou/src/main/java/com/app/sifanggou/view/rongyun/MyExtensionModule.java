package com.app.sifanggou.view.rongyun;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2017/12/5.
 */

public class MyExtensionModule extends DefaultExtensionModule {
    private MyPlugin myPlugin;
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules =  super.getPluginModules(conversationType);
//        pluginModules.add(myPlugin);
//        return pluginModules;

//        IPluginModule location = new DefaultLocationPlugin();
//
//        if (conversationType.equals(Conversation.ConversationType.GROUP) ||
//                conversationType.equals(Conversation.ConversationType.DISCUSSION) ||
//                conversationType.equals(Conversation.ConversationType.PRIVATE)) {
//            pluginModules.add(location);
//        } else {
//
//        }

//        List<IPluginModule> pluginModules = new ArrayList<IPluginModule>();
//        ImagePlugin imagePlugin = new ImagePlugin();
//        pluginModules.add(imagePlugin);
        return pluginModules;
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        return super.getEmoticonTabs();
    }
}
