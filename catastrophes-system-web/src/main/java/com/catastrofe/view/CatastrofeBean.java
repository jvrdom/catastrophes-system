package com.catastrofe.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.json.JSONException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;

import com.catastrofe.dao.CatastrofeDao;
import com.catastrofe.dao.OngDao;
import com.catastrofe.dao.UsuarioDao;
import com.catastrofe.model.Catastrofe;
import com.catastrofe.model.Imagen;
import com.catastrofe.model.Novedades;
import com.catastrofe.model.Ong;
import com.catastrofe.model.Plan;
import com.catastrofe.model.tipoPlan;
import com.catastrofe.utiles.AndroidGCMPushNotification;
import com.catastrofe.utiles.UtilesWeb;

/**
 * Backing bean for Catastrofe entities.
 * <p>
 * This class provides CRUD functionality for all Catastrofe entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class CatastrofeBean implements Serializable {
	
	@Inject
	private OngDao ongDao;
	
	private static final long serialVersionUID = 1L;
	private static final String RESCATISTA = "Rescatista";

	/*
	 * Support creating and retrieving Catastrofe entities
	 */

	private Long id;
	private String latLng, url;
	private double radio;
	private UtilesWeb utiles;
	private Set<Plan> planes;
	private Set<Imagen> imagenesCatastrofe;
	private Set<Novedades> novedadesCatastrofe;
	private String estilo;
	private AndroidGCMPushNotification notifications;
	
	private DualListModel<Ong> ongPick;
	private List<Ong> ongSelected;
	private List<Ong> ongs;
	private String estiloCss;
	
	public CatastrofeBean() {
		utiles = new UtilesWeb();
		planes = new HashSet<Plan>();
		imagenesCatastrofe = new HashSet<Imagen>();
		notifications = new AndroidGCMPushNotification();
	}
	
	@PostConstruct
	public void init(){
		ongSelected=new ArrayList<Ong>();
		ongs=new ArrayList<>();
		ongPick = new DualListModel<>(ongSelected, ongs);
		estiloCss="tema1.css";
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	
	public String getEstiloCss() {
		return estiloCss;
	}

	public void setEstiloCss(String estiloCss) {
		this.estiloCss = estiloCss;
	}
	
	public DualListModel<Ong> getOngPick() {
		return ongPick;
	}

	public void setOngPick(DualListModel<Ong> ongPick) {
		this.ongPick = ongPick;
	}

	public List<Ong> getOngSelected() {
		return ongSelected;
	}

	public void setOngSelected(List<Ong> ongSelected) {
		this.ongSelected = ongSelected;
	}

	public List<Ong> getOngs() {
		return ongs;
	}

	public void setOngs(List<Ong> ongs) {
		this.ongs = ongs;
	}



	private Catastrofe catastrofe;

	public Catastrofe getCatastrofe() {
		return this.catastrofe;
	}

	@Inject
	private Conversation conversation;

	@Inject
	private CatastrofeDao catastofeDao;
	
	@Inject
	private UsuarioDao usuarioDao;

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public String create() {
		this.conversation.begin();
		return "create?faces-redirect=true";
	}

	public void retrieve() {
		estilo= estiloCss;
		estilo=estilo.replaceAll(".css", "");
		System.out.println("estilo en retrive: "+estilo);
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}

		if (this.id == null) {
			this.catastrofe = this.example;
		} else {
			this.catastrofe = findById(getId());
			estilo=catastrofe.getCss();
		}
	}

	public Catastrofe findById(Long id) {

		return this.catastofeDao.findById(id);
	}

	/*
	 * Support updating and deleting Catastrofe entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				System.out.println("para crear catastrofe----");
				latLng = latLng.replace("(", "");
				latLng = latLng.replace(")", "");

				String[] latlong = latLng.split(",");
				double lat = Double.parseDouble(latlong[0]);
				double lng = Double.parseDouble(latlong[1]);

				this.catastrofe.setLatitud(lat);
				this.catastrofe.setLongitud(lng);
				this.catastrofe.setRadio(this.radio);
				this.catastrofe.setPlanes(this.planes);
				if(estilo!=null && !estilo.isEmpty()){
					this.catastrofe.setCss(estilo.trim()+".css");
				}
				this.catastofeDao.create(this.catastrofe);
				
				this.catastrofe=this.catastofeDao.findByName(this.catastrofe.getNombre());
				System.out.println("findByName catId:"+this.catastrofe.getId());
				if(ongPick.getSource()!=null && !ongPick.getSource().isEmpty()){
					System.out.println("ongSelected no es null");
					for(Ong ong: ongPick.getSource()){
						System.out.println("en el for");
						if(ong.getCatastrofes()==null){
							Set<Catastrofe> set = new HashSet<Catastrofe>();
							set.add(catastrofe);
							ong.setCatastrofes(set);
						}else{
							ong.getCatastrofes().add(catastrofe);
						}
						ongDao.update(ong);
					}
				}
				this.sendNotification(RESCATISTA, this.catastrofe);
				
				return "./../usuario/index.xhtml";
				
			} else {
				
				if(!this.imagenesCatastrofe.isEmpty()) {
					this.catastrofe.setImagenes(this.imagenesCatastrofe);
				}
				
				if(!this.url.isEmpty()){
					Novedades novedad = new Novedades();
					novedad.setDescripcion("chuco");
					
					novedad.setThumbnail(utiles.getThumbUrl(this.url));
					novedad.setOrigenDato(this.url);
					
					novedadesCatastrofe = this.catastrofe.getNovedades();
					novedadesCatastrofe.add(novedad);
					
					this.catastrofe.setNovedades(this.novedadesCatastrofe);
				}
				
				this.catastofeDao.update(this.catastrofe);
				return "view?faces-redirect=true&id=" + this.catastrofe.getId();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public String delete() {
		this.conversation.end();

		try {
			Catastrofe deletableEntity = findById(getId());

			this.catastofeDao.deleteById(deletableEntity.getId());
			// this.entityManager.remove(deletableEntity);
			this.entityManager.flush();
			return "search?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public void handleFileUploadImagen(FileUploadEvent event) {
		try {
			this.catastrofe.setLogo(utiles.fileUpload(event.getFile().getFileName(), event.getFile().getInputstream()));
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito", event.getFile().getFileName() + " esta subido.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo!","Fallo al subir archivo: " + event.getFile().getFileName() + ", razón: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			
			String fileName = event.getFile().getFileName();
			Plan plan = new Plan();
			plan.setUrl(utiles.fileUpload(fileName, event.getFile().getInputstream()));
			
			if(utiles.isEmergencia(fileName)) {
				plan.setDescripcion("Plan de Emergencia");
				plan.setTipo(tipoPlan.Emergencia);
			} else {
				plan.setDescripcion("Plan de Gestión de Riesgos");
				plan.setTipo(tipoPlan.Riesgo);
			}

			planes.add(plan);

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito", event.getFile().getFileName()+ " esta subido.");
			FacesContext.getCurrentInstance().addMessage(null, message);

		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo!","Fallo al subir el archivo: " + event.getFile().getFileName() + ", razón: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void handleGalleria(FileUploadEvent event) {
		
		Imagen imagen = new Imagen();
		
		try {
			imagen.setImagen(utiles.fileUpload(event.getFile().getFileName(), event.getFile().getInputstream()));
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo!", "Fallo al subir el archivo: " + event.getFile().getFileName() + ", razón: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		imagenesCatastrofe = this.catastrofe.getImagenes();
		imagenesCatastrofe.add(imagen);
		
		this.update();
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,	"Exito", event.getFile().getFileName() + " esta subido.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void handleKeyEvent(){
		try{
			this.update();
		}catch(Exception ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",ex.getMessage()));
		}
	}
	
	private void sendNotification(String rolUsuario, Object object){
	    try {
	    	notifications.enviarNotificaciones("10", usuarioDao.getRegIDs(rolUsuario), object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }

	public String getLatLng() {
		return latLng;
	}

	public void setLatLng(String latLng) {
		this.latLng = latLng;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * Support searching Catastrofe entities with pagination
	 */

	private int page;
	private long count;
	private List<Catastrofe> pageItems;

	private Catastrofe example = new Catastrofe();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Catastrofe getExample() {
		return this.example;
	}

	public void setExample(Catastrofe example) {
		this.example = example;
	}

	public void search() {
		this.page = 0;
	}

	public void paginate() {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Catastrofe> root = countCriteria.from(Catastrofe.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Catastrofe> criteria = builder
				.createQuery(Catastrofe.class);
		root = criteria.from(Catastrofe.class);
		TypedQuery<Catastrofe> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Catastrofe> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String nombre = this.example.getNombre();
		if (nombre != null && !"".equals(nombre)) {
			predicatesList.add(builder.like(root.<String> get("nombre"),
					'%' + nombre + '%'));
		}
		String descripcion = this.example.getDescripcion();
		if (descripcion != null && !"".equals(descripcion)) {
			predicatesList.add(builder.like(root.<String> get("descripcion"),
					'%' + descripcion + '%'));
		}
		String logo = this.example.getLogo();
		if (logo != null && !"".equals(logo)) {
			predicatesList.add(builder.like(root.<String> get("logo"),
					'%' + logo + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<Catastrofe> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Catastrofe entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Catastrofe> getAll() {
		try {
			CriteriaQuery<Catastrofe> criteria = this.entityManager.getCriteriaBuilder().createQuery(Catastrofe.class);
			return this.entityManager.createQuery(criteria.select(criteria.from(Catastrofe.class))).getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
			return null;
		}
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final CatastrofeBean ejbProxy = this.sessionContext
				.getBusinessObject(CatastrofeBean.class);

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				return ejbProxy.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((Catastrofe) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Catastrofe add = new Catastrofe();

	public Catastrofe getAdd() {
		return this.add;
	}

	public Catastrofe getAdded() {
		Catastrofe added = this.add;
		this.add = new Catastrofe();
		return added;
	}
	
	public void cargarOngs(){
		ongs=ongDao.listAll(null, null);
		
		ongPick = new DualListModel<>(ongSelected, ongs);
	}
	public void cancelarAsigOng(){
		ongSelected=new ArrayList<Ong>();
	}
	public void asignarOng(){
		
		System.out.println("cantidad de ong selec:"+ongPick.getSource().size());
		for(Ong o:ongPick.getSource()){
			System.out.println("valor:"+o.getNombre());
		}
		System.out.println("cantidad de ong2:"+ongPick.getTarget().size());
		
	}
	
	
	public Converter getOngConverter() {
        return new Converter() {
            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String value) {
                if (value.trim().equals("")) {
                    return null;
                } else {
                    try {
                        long number = Long.parseLong(value);

                        for (Ong u : ongs) {
                            if (u.getId() == number) {
                                return u;
                            }
                        }

                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid User"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value) {
                if (value == null || value.equals("")) {
                    return "";
                } else {
                    return String.valueOf(((Ong) value).getId());
                }

            }
        };
    }
	
	public void seleccionarTema(ValueChangeEvent e){
		
		estiloCss=e.getNewValue().toString()+".css";
		System.out.println("Ingreso a seleccionar tema***************************************:"+estiloCss);
		
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			context.redirect(context.getRequestContextPath() + "/catastrofe/create.xhtml");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}