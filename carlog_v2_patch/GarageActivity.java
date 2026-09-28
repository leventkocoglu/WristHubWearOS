package com.levent.carlog;

import android.os.Bundle;
import android.content.res.ColorStateList;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class GarageActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private LinearLayout listContainer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);
        setContentView(buildContent());
        getWindow().setStatusBarColor(getColor(R.color.bg));
        getWindow().setNavigationBarColor(getColor(R.color.bg));
    }

    @Override protected void onResume() { super.onResume(); renderVehicles(); }

    private View buildContent() {
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(getColor(R.color.bg));
        root.setPadding(dp(20), dp(18), dp(20), dp(18));

        LinearLayout header = new LinearLayout(this); header.setGravity(Gravity.CENTER_VERTICAL);
        MaterialButton back = button("‹", false); back.setOnClickListener(v -> finish()); header.addView(back, new LinearLayout.LayoutParams(dp(48), dp(48)));
        LinearLayout titles = new LinearLayout(this); titles.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f); tp.setMarginStart(dp(8));
        TextView small = text("GARAGE", 10, R.color.accent, true); small.setLetterSpacing(.14f); titles.addView(small);
        titles.addView(text("Araçların", 25, R.color.text_primary, true)); header.addView(titles, tp);
        MaterialButton add = button("＋ Ekle", true); add.setOnClickListener(v -> showVehicleDialog(null)); header.addView(add);
        root.addView(header);

        TextView expl = text("Bir araca dokunup aktif hale getir. Her aracın bakım, yakıt ve internet verisi ayrı tutulur.", 12, R.color.text_secondary, false);
        expl.setPadding(0, dp(14), 0, dp(10)); root.addView(expl);

        ScrollView scroll = new ScrollView(this); listContainer = new LinearLayout(this); listContainer.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(listContainer, new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.WRAP_CONTENT));
        root.addView(scroll, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));
        return root;
    }

    private void renderVehicles() {
        listContainer.removeAllViews();
        List<Vehicle> vehicles = db.getVehicles();
        if (vehicles.isEmpty()) {
            TextView empty = text("Henüz araç yok. Sağ üstten ilk aracını ekle.", 14, R.color.text_secondary, false);
            empty.setPadding(dp(18), dp(20), dp(18), dp(20)); empty.setBackgroundResource(R.drawable.card);
            listContainer.addView(empty);
            return;
        }
        for (Vehicle v : vehicles) listContainer.addView(vehicleCard(v), cardParams());
    }

    private View vehicleCard(Vehicle v) {
        LinearLayout card = new LinearLayout(this); card.setOrientation(LinearLayout.VERTICAL); card.setPadding(dp(14), dp(14), dp(14), dp(14)); card.setBackgroundResource(R.drawable.card);
        LinearLayout top = new LinearLayout(this); top.setGravity(Gravity.CENTER_VERTICAL);
        ImageView image = new ImageView(this); image.setScaleType(ImageView.ScaleType.CENTER_CROP); image.setImageResource(R.drawable.ic_car); image.setBackgroundColor(getColor(R.color.surface_3));
        if (v.photoUrl != null && !v.photoUrl.isBlank()) Picasso.get().load(v.photoUrl).placeholder(R.drawable.ic_car).error(R.drawable.ic_car).fit().centerCrop().into(image);
        top.addView(image, new LinearLayout.LayoutParams(dp(96), dp(76)));

        LinearLayout mid = new LinearLayout(this); mid.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams mp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f); mp.setMarginStart(dp(14));
        mid.addView(text(v.displayName(), 18, R.color.text_primary, true));
        String sub = v.subtitle() + (v.mileage > 0 ? " • " + String.format("%,d km", v.mileage).replace(',', '.') : "");
        mid.addView(text(sub, 11, R.color.text_secondary, false));
        TextView status = text(v.active ? "● AKTİF" : (v.updatedAt > 0 ? "● VERİ EŞİTLENDİ" : "○ VERİ BEKLİYOR"), 9, v.active ? R.color.accent : R.color.text_secondary, true);
        status.setPadding(0, dp(6), 0, 0); mid.addView(status); top.addView(mid, mp); card.addView(top);

        LinearLayout actions = new LinearLayout(this); actions.setGravity(Gravity.END); actions.setPadding(0, dp(12), 0, 0);
        MaterialButton select = button(v.active ? "Aktif" : "Seç", v.active); select.setEnabled(!v.active);
        select.setOnClickListener(x -> { db.setActiveVehicle(v.id); renderVehicles(); });
        MaterialButton edit = button("Düzenle", false); edit.setOnClickListener(x -> showVehicleDialog(v));
        actions.addView(select); LinearLayout.LayoutParams ep = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dp(44)); ep.setMarginStart(dp(8)); actions.addView(edit, ep);
        card.addView(actions);
        card.setOnClickListener(x -> { if (!v.active) { db.setActiveVehicle(v.id); renderVehicles(); } });
        return card;
    }

    private void showVehicleDialog(Vehicle existing) {
        boolean editing = existing != null;

        LinearLayout dialogContent = new LinearLayout(this);
        dialogContent.setOrientation(LinearLayout.VERTICAL);
        dialogContent.setPadding(dp(4), dp(2), dp(4), 0);

        TextView info = text("Kaydedince araç görseli ve internet bilgileri arka planda aranır.", 12, R.color.text_secondary, false);
        info.setPadding(dp(4), 0, dp(4), dp(8));
        dialogContent.addView(info);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setVerticalScrollBarEnabled(true);
        scroll.setClipToPadding(false);
        scroll.setPadding(0, 0, dp(4), dp(12));

        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(dp(4), dp(2), dp(4), dp(12));
        scroll.addView(form, new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.WRAP_CONTENT));

        TextInputEditText brand = addField(form, "Marka", InputType.TYPE_CLASS_TEXT, editing ? existing.brand : "");
        TextInputEditText model = addField(form, "Model", InputType.TYPE_CLASS_TEXT, editing ? existing.model : "");
        TextInputEditText variant = addField(form, "Motor / paket (önerilir)", InputType.TYPE_CLASS_TEXT, editing ? existing.variant : "");
        TextInputEditText year = addField(form, "Model yılı (1999+)", InputType.TYPE_CLASS_NUMBER, editing ? String.valueOf(existing.year) : "");
        TextInputEditText fuel = addField(form, "Yakıt (Dizel, Benzin, Hibrit...)", InputType.TYPE_CLASS_TEXT, editing ? existing.fuel : "");
        TextInputEditText transmission = addField(form, "Şanzıman", InputType.TYPE_CLASS_TEXT, editing ? existing.transmission : "");
        TextInputEditText mileage = addField(form, "Güncel kilometre", InputType.TYPE_CLASS_NUMBER, editing && existing.mileage > 0 ? String.valueOf(existing.mileage) : "");
        TextInputEditText plate = addField(form, "Plaka (opsiyonel)", InputType.TYPE_CLASS_TEXT, editing ? existing.plate : "");
        TextInputEditText color = addField(form, "Renk (opsiyonel)", InputType.TYPE_CLASS_TEXT, editing ? existing.color : "");
        TextInputEditText city = addField(form, "Şehir", InputType.TYPE_CLASS_TEXT, editing ? existing.city : "İstanbul");

        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int formHeight = Math.min(dp(430), (int) (screenHeight * 0.46f));
        dialogContent.addView(scroll, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, formHeight));

        LinearLayout actions = new LinearLayout(this);
        actions.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        actions.setPadding(0, dp(10), 0, dp(2));

        MaterialButton cancel = button("Vazgeç", false);
        actions.addView(cancel, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, dp(46)));

        MaterialButton delete = null;
        if (editing) {
            delete = button("Sil", false);
            LinearLayout.LayoutParams deleteParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, dp(46));
            deleteParams.setMarginStart(dp(8));
            actions.addView(delete, deleteParams);
        }

        MaterialButton save = button(editing ? "Kaydet" : "Aracı ekle", true);
        LinearLayout.LayoutParams saveParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, dp(46));
        saveParams.setMarginStart(dp(8));
        actions.addView(save, saveParams);
        dialogContent.addView(actions);

        AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setTitle(editing ? "Aracı düzenle" : "Garaja araç ekle")
                .setView(dialogContent)
                .create();

        cancel.setOnClickListener(v -> dialog.dismiss());
        if (editing && delete != null) {
            MaterialButton finalDelete = delete;
            finalDelete.setOnClickListener(v -> confirmDelete(existing, dialog));
        }

        save.setOnClickListener(v -> {
            String brandText = value(brand), modelText = value(model), yearText = value(year);
            if (brandText.isBlank()) { brand.setError("Marka gerekli"); brand.requestFocus(); return; }
            if (modelText.isBlank()) { model.setError("Model gerekli"); model.requestFocus(); return; }

            int y;
            try { y = Integer.parseInt(yearText); }
            catch (Exception e) { year.setError("Geçerli yıl gir"); year.requestFocus(); return; }

            int maxYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
            if (y < 1999 || y > maxYear) {
                year.setError("1999 - " + maxYear + " arası yıl gir");
                year.requestFocus();
                return;
            }

            long km = 0;
            try {
                if (!value(mileage).isBlank()) km = Long.parseLong(value(mileage));
            } catch (Exception e) {
                mileage.setError("Kilometreyi kontrol et");
                mileage.requestFocus();
                return;
            }

            Vehicle x = editing ? existing : new Vehicle();
            x.brand = brandText;
            x.model = modelText;
            x.variant = value(variant);
            x.year = y;
            x.fuel = value(fuel);
            x.transmission = value(transmission);
            x.mileage = km;
            x.plate = value(plate).toUpperCase();
            x.color = value(color);
            x.city = value(city);
            x.active = editing ? existing.active : true;

            if (editing) db.updateVehicle(x);
            else x.id = db.insertVehicle(x);
            if (!editing) db.setActiveVehicle(x.id);

            dialog.dismiss();
            renderVehicles();
            Toast.makeText(this, "Araç kaydedildi • internet verisi aranıyor", Toast.LENGTH_SHORT).show();
            new VehicleDataService(this).syncVehicleAsync(db.getVehicle(x.id),
                    (ok, msg) -> runOnUiThread(this::renderVehicles));
        });

        dialog.setOnShowListener(d -> {
            if (dialog.getWindow() != null) {
                dialog.getWindow().setSoftInputMode(
                        android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
        });
        dialog.show();
    }

    private void confirmDelete(Vehicle vehicle, AlertDialog parent) {
        new MaterialAlertDialogBuilder(this).setTitle("Aracı sil?").setMessage(vehicle.displayName() + " ve bu araca ait kayıtlar silinecek.")
                .setNegativeButton("Vazgeç", null).setPositiveButton("Sil", (d, w) -> { db.deleteVehicle(vehicle.id); parent.dismiss(); renderVehicles(); }).show();
    }

    private TextInputEditText addField(LinearLayout parent, String hint, int inputType, String value) {
        TextInputLayout l = new TextInputLayout(this); l.setHint(hint); l.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE); l.setBoxCornerRadii(dp(14),dp(14),dp(14),dp(14));
        TextInputEditText e = new TextInputEditText(l.getContext()); e.setInputType(inputType); e.setText(value); e.setSingleLine(true); l.addView(e);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); p.topMargin = dp(9); parent.addView(l, p); return e;
    }

    private MaterialButton button(String label, boolean filled) {
        MaterialButton b = new MaterialButton(this); b.setText(label); b.setAllCaps(false); b.setTextSize(12); b.setMinWidth(0); b.setCornerRadius(dp(14));
        if (filled) { b.setBackgroundColor(getColor(R.color.accent)); b.setTextColor(0xFF07110E); }
        else { b.setBackgroundColor(getColor(R.color.surface_2)); b.setTextColor(getColor(R.color.text_primary)); b.setStrokeColor(ColorStateList.valueOf(getColor(R.color.stroke))); b.setStrokeWidth(dp(1)); }
        return b;
    }

    private LinearLayout.LayoutParams cardParams() { LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); p.topMargin = dp(10); return p; }
    private TextView text(String value, int size, int color, boolean bold) { TextView t = new TextView(this); t.setText(value); t.setTextSize(size); t.setTextColor(getColor(color)); if (bold) t.setTypeface(null, android.graphics.Typeface.BOLD); return t; }
    private String value(EditText e) { return e.getText() == null ? "" : e.getText().toString().trim(); }
    private int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density + .5f); }
}
\n