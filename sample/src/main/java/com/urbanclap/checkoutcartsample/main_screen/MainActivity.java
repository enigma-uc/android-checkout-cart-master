package com.urbanclap.checkoutcartsample.main_screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.urbanclap.checkoutcart.frame_work.market.MarketManager;
import com.urbanclap.checkoutcart.frame_work.market.Section;
import com.urbanclap.checkoutcart.market_impl.recycler_view_market.RecyclerMarketManager;
import com.urbanclap.checkoutcart.market_impl.recycler_view_market.RecyclerMarketView;
import com.urbanclap.checkoutcart.utils.DefaultCart;
import com.urbanclap.checkoutcart.utils.DefaultCartItem;
import com.urbanclap.checkoutcart.utils.MarketUtils;
import com.urbanclap.checkoutcartsample.R;
import com.urbanclap.checkoutcartsample.market.ItemFactory;
import com.urbanclap.checkoutcartsample.market.NavigationItemFactory;
import com.urbanclap.checkoutcartsample.market.entity.PokemonCartBaseItem;
import com.urbanclap.checkoutcartsample.model.PokemonItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private RecyclerMarketView marketView;
    private MainContract.Presenter presenter;
    private RecyclerMarketManager rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marketView = findViewById(R.id.market);
        this.presenter = new MainPresenter(this, new JsonLoader(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void showSections(@NonNull List<Section<PokemonCartBaseItem>> sections, @NonNull List<Section<PokemonCartBaseItem>> sections2) {
        MarketManager.Config<PokemonCartBaseItem, String, DefaultCartItem<PokemonItem>> config =
                new MarketManager.Config<>(sections);

        config.setCart(new DefaultCart<DefaultCartItem<PokemonItem>>())
                .setSticky(MarketUtils.getDefaultStickyView(this))
                .setNavigator(
                        MarketUtils.getDefaultHorizontalNavigationBar(this),
                        new NavigationItemFactory()
                );
        rcv = new RecyclerMarketManager<>(this, config, new ItemFactory(), marketView);
        marketView.reference(rcv);
        rcv.updateSections2(sections2);
        // marketView.duplicateList(sections2);
        marketView.bindMarketManager(rcv);
    }

    @Override
    public void showError(@NonNull String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

}
