package studio.xmatrix.coffee.ui.person;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.view.View;
import studio.xmatrix.coffee.databinding.PersonalNameDialogBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;

import javax.inject.Inject;

public class NameDialogHandler implements Injectable {

    private PersonActivity activity;
    private PersonalNameDialogBinding binding;
    private String name;

    @Inject
    private
    ViewModelProvider.Factory viewModelFactory;
    private PersonViewModel viewModel;

    NameDialogHandler(PersonActivity activity, PersonalNameDialogBinding binding, String name){
        this.activity = activity;
        this.binding = binding;
        this.name = name;

        AppInjector.Companion.inject(this);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(PersonViewModel.class);

        initView();
    }

    private void initView() {
        binding.name.setText(name);
        binding.button.setOnClickListener(v -> save());
    }

    private void save() {
        String saveName = binding.name.getText().toString();
        if (saveName.equals(name)){

        } else {
            
        }
    }
}
