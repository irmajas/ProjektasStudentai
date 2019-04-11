Naudojama Java, log4j, Junit

Programa tikrina studentų testų rezultatus. Programai nurodomi du argumentai:
-direktorija, kur saugomi failai su teisingais atsakymas
-direktorija, kur kaupiami kiekvieno testo studento pasirinkti atsakymų variantai.

Programa muskaito teisingų atsakymų failus, tai pat ir studentų atsakymų failus. paskaičiuoja kiekvieno testo rezultatus(procentais) ir rezultatų aplanke patikrina jau esamus rezultatus, jei studentas jau sprendė šį testą, laikoma, kad jis jį perlaikė ir įrašomas naujas rezultattas.
jei rezultatų direktorijos nėra, ji sukuriama.
Programa tikrna trijų tipų testus:

-vienas teisingas atsakymas

-keli teisingi atsakymai

-kai įrašomi vienas arba keli atsakymo žodžiai.

Informacija visuose failuose saugoma Jason formatu.
Informacija, kaip vyksta procesai loginama į failą Application.log
Programa padengta daugiau nei 10 unit testų.
